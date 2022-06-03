<template>
  <div style="color: #dadada;" class="mx-auto mb-4">
    <span v-if="movieLst[0].Title === 'Not found'">No results found for <strong>{{ movieLst[0].Search }}</strong> </span>
    <div v-if="movieLst[0].Title !== 'No value' && movieLst[0].Title !== 'Not found'" class="d-flex flex-wrap gap-5 flex-grow-1 mt-4 w-100">
      <div v-for="(title, idx) in movieLst" :key="idx" v-b-tooltip.hover :title="`${title.Year}, ${title.Title}`" @click="openModal(title.imdbID)">
        <b-img v-if="title.Poster !== 'N/A'" fluid thumbnail style="height: 390px; width: 260px" :src="title.Poster" :alt="title.Title"></b-img>
        <div v-else style="height: 390px; width: 260px; border: 4px groove white" class="p-3">
          <span style="color: #b9b9b9">{{ title.Title }}</span>
          <span style="color: #484747; font-size: 200px" class="material-symbols-outlined">theaters</span>
        </div>
      </div>
    </div>
    <b-modal id="item-modal" size="lg" hide-footer>
      <template #modal-header="{ close }">
        <h5>{{ oneItem.Year }}, {{ oneItem.Title }}</h5>
          <span @click="close()" class="material-symbols-outlined rounded-2" style="font-size: 28px; border: 1px groove red">close</span>
      </template>
        <b-card body-bg-variant="light" :img-src="oneItem.Poster" :img-alt="oneItem.Title" img-left class="mb-3">
          <b-card-text >
            {{ oneItem.Plot }}
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
      oneItem:''
    }
  },
  methods: {
    openModal(id){
      axios.get("http://localhost:8080/api/one?imdbID=" + id)
          .then((res) => {
            console.log(res)
            this.oneItem = res.data
            this.$bvModal.show('item-modal')
          })
          .catch((err) => {
            if(err.response){
              console.error(err.response)
            }
    })
  }
  }
}
</script>

<style scoped>

</style>
