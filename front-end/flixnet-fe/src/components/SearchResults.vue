<template>
  <div style="color: #dadada;" class="mx-auto mb-4 p-2">
    <!--  search result-->
    <span v-if="movieLst[0].Title === 'Not found'">No results found for <strong>{{ movieLst[0].Search }}</strong></span>
    <div v-if="movieLst[0].Title !== 'No value' && movieLst[0].Title !== 'Not found'" class="text-start mt-4">
      <span style="font-size: 28px;">Search Results For <strong>{{ movieLst[0].Search}}</strong></span>
      <hr style="border: 1px solid white">
    </div>
    <div v-if="movieLst[0].Title !== 'No value' && movieLst[0].Title !== 'Not found'" class="d-flex flex-wrap gap-5 flex-grow-1 w-100">
      <div v-for="(title, idx) in movieLst" :key="idx"  @mouseover="overedItem($event, title.imdbID)" @mouseleave="overedItem($event, '')" style="position: relative" v-b-tooltip.hover :title="`${title.Year}, ${title.Title}`">
        <div v-b-hover="mainHandleHover">
          <b-icon v-if="selectedItem === title.imdbID && !mainhoverHeartBool" class="h1 heartPosition" icon="heart" variant="danger"></b-icon>
          <b-icon v-if="(selectedItem === title.imdbID && mainhoverHeartBool) || favouritiesLst.filter(f => f.imdbID === title.imdbID).length > 0" @click="setItem(title)"  class="h1 heartPosition" icon="heart-fill" variant="danger"></b-icon>
        </div>
        <b-img v-if="title.Poster !== 'N/A'" @click="openModal(title.imdbID)" fluid class="posterSize" :src="title.Poster" :alt="title.Title"></b-img>
        <div v-else @click="openModal(title.imdbID)" class="p-3 rounded-2 posterSize">
          <span style="color: #b9b9b9">{{ title.Title }}</span>
          <span style="color: #484747; font-size: 200px" class="material-symbols-outlined">theaters</span>
        </div>
      </div>
    </div>
    <!--    whish/favourited list-->
    <div v-if="favouritiesLst.length > 0" class="text-start mt-4">
      <span style="font-size: 28px;">Your Wishlist</span>
      <hr style="border: 1px solid white">
    </div>
    <div v-if="favouritiesLst.length > 0" class="d-flex flex-wrap gap-5 flex-grow-1 w-100" :key="renderAgain">
      <div v-for="(title, idx) in favouritiesLst" :key="idx" v-b-tooltip.hover :title="`${title.Year}, ${title.Title}`" style="position: relative">
        <div>
          <b-icon @click="setItem(title)" v-if="favouritiesLst.filter(f => f.imdbID === title.imdbID).length > 0" class="h1 heartPosition" icon="heart-fill" variant="danger"></b-icon>
        </div>
        <b-img v-if="title.Poster !== 'N/A'" @click="openModal(title.imdbID)" fluid class="posterSize" :src="title.Poster" :alt="title.Title"></b-img>
        <div v-else @click="openModal(title.imdbID)" class="p-3 rounded-2 posterSize">
          <span style="color: #b9b9b9">{{ title.Title }}</span>
          <span style="color: #484747; font-size: 200px" class="material-symbols-outlined">theaters</span>
        </div>
      </div>
    </div>
    <!--    modal-->
    <b-modal style="position: relative" id="item-modal" size="lg" hide-footer header-bg-variant="dark" header-text-variant="light">
      <template #modal-header="{ close }">
        <h5>{{ oneItem.Year }}, {{ oneItem.Title }}</h5>
        <span @click="close()" class="material-symbols-outlined rounded-2" role="button" style="font-size: 28px; border: 1px groove red">close</span>
      </template>
        <div v-if="loading" class="text-center p-3">
          <b-spinner variant="secondary" style="width: 6rem; height: 6rem;"></b-spinner>
        </div>
        <b-card :key="renderAgain" v-else-if="oneItem.Poster !== 'N/A'" body-bg-variant="light" class="mb-3">
          <b-row>
            <b-col md="5">
              <div>
                <b-icon @click="setItem(oneItem)" class="h1 heartPosition" icon="heart" variant="danger"></b-icon>
                <b-icon v-if="favouritiesLst.filter(f => f.imdbID === oneItem.imdbID).length > 0" @click="setItem(oneItem)"  class="h1 heartPosition" icon="heart-fill" variant="danger"></b-icon>
              </div>
              <b-card-img :src="oneItem.Poster" :img-alt="oneItem.Title"></b-card-img>
            </b-col>
            <b-col md="7">
              <b-card-text>
                Plot: {{ oneItem.Plot }}
              </b-card-text>
              <b-card-text>
                Genre: {{ oneItem.Genre }}
              </b-card-text>
              <b-card-text>
                Director: {{ oneItem.Director }}
              </b-card-text>
              <b-card-text>
                Actors: {{ oneItem.Actors }}
              </b-card-text>
              <b-card-text>
                <b-form-rating size="sm" variant="warning" v-model="oneItem.imdbRating" stars="10" readonly show-value inline no-border disabled></b-form-rating>
              </b-card-text>
              <b-card-text v-if="oneItem.Website !== 'N/A' && oneItem.Website !== null">
                Website: {{ oneItem.Website }}
              </b-card-text>
            </b-col>
          </b-row>
        </b-card>
        <b-card v-else body-bg-variant="light" class="mb-3 ps-4">
          <div>
            <b-icon @click="setItem(oneItem)" class="h1 heartPosition" icon="heart" variant="danger"></b-icon>
            <b-icon v-if="favouritiesLst.filter(f => f.imdbID === oneItem.imdbID).length > 0" @click="setItem(oneItem)"  class="h1 heartPosition" icon="heart-fill" variant="danger"></b-icon>
          </div>
          <b-card-text >
            Plot: {{ oneItem.Plot }}
          </b-card-text>
          <b-card-text >
            Genre: {{ oneItem.Genre }}
          </b-card-text>
          <b-card-text >
            Director: {{ oneItem.Director }}
          </b-card-text>
          <b-card-text >
            Actors: {{ oneItem.Actors }}
          </b-card-text>
          <b-card-text >
            <b-form-rating size="sm" variant="warning" v-model="oneItem.imdbRating" stars="10" readonly show-value inline no-border disabled></b-form-rating>
          </b-card-text>
          <b-card-text v-if="oneItem.Website !== 'N/A'">
            Website: {{ oneItem.Website }}
          </b-card-text>
        </b-card>
    </b-modal>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "SearchResults",
  data () {
    return {
      get movieLst(){
        return JSON.parse(localStorage.getItem("movieLst"))
      },
      oneItem:'',
      loading: false,
      mainhoverHeartBool: false,
      hoverHeartBool: false,
      selectedItem: '',
      get favouritiesLst(){
        return JSON.parse(localStorage.getItem("favouritesLst"))
      },
      renderAgain: false
    }
  },
  methods: {
    setItem(item){
      console.log(item)
      let favourities = JSON.parse(localStorage.getItem("favouritesLst")) || []
      if (favourities.filter(f => f.imdbID === item.imdbID).length === 0){
        favourities.push(item)
        localStorage.setItem("favouritesLst", JSON.stringify(favourities))
        this.renderAgain = !this.renderAgain
      } else {
        favourities = favourities.filter((value) => value.imdbID !== item.imdbID)
        localStorage.setItem("favouritesLst", JSON.stringify(favourities))
        this.renderAgain = !this.renderAgain
      }
    },
    mainHandleHover(isHovered){
      this.mainhoverHeartBool = isHovered
    },
    overedItem(e, element){
      this.selectedItem = element
      //this.mainhoverHeartBool = isHovered
    },
    handleHover (isHovered){
      this.hoverHeartBool = isHovered
    },
    openModal(id){
      this.loading = true
      axios.get("http://localhost:8080/api/one?imdbID=" + id)
          .then((res) => {
            this.oneItem = res.data
            this.$bvModal.show('item-modal')
            // loading behaviour - simulating slow networks
            setTimeout(()=> {
              this.loading = false
            }, 500)
          })
          .catch((err) => {
            if(err.response){
              console.error(err.response)
            }
            this.loading = false
          })
    }
  },
  mounted() {

  }
}
</script>

<style scoped>
.heartPosition{
  position: absolute;
  left: 10px;
  top: 10px
}
.posterSize{
  height: 390px;
  width: 260px;
  border: 4px solid white
}
</style>
