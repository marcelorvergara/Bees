<template>
<div style="color: white">
  TESTE
  {{ movieLst }}
</div>
</template>

<script>
export default {
  name: "SearchResults",
  data () {
    return {
      get movieLst(){
        return localStorage.getItem("movieLst")
      }
    }
  }
}
</script>

<style scoped>

</style>
