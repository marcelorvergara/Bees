<template>
  <div style="color: #dadada;" class="mx-auto mb-4">
    <span v-if="movieLst[0].Title === 'Not found'">No results found for <strong>{{ movieLst[0].Search }}</strong> </span>
    <div v-if="movieLst[0].Title !== 'No value' && movieLst[0].Title !== 'Not found'" class="d-flex flex-wrap gap-5 flex-grow-1 mt-4 w-100">
      <div v-for="(title, idx) in movieLst" :key="idx" v-b-tooltip.hover :title="`${title.Year}, ${title.Title}`">
        <b-img fluid thumbnail style="height: 390px; width: 260px" :src="title.Poster" :alt="title.Title"></b-img>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SearchResults",
  data () {
    return {
      get movieLst(){
        return JSON.parse(localStorage.getItem("movieLst"))
      }
    }
  }
}
</script>

<style scoped>

</style>
