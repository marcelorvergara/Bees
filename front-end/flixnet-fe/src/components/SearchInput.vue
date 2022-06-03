<template>
  <div class="container mx-auto pt-4">
    <b-row class="justify-content-center">
      <b-col cols="6" class="d-flex">
        <span style="font-size: 36px; color: white" class="material-symbols-outlined pe-1">search</span>
        <b-form-input
            style="border: 2px groove #e0bc00; background-color: #212529; color: white"
            placeholder="Search something"
            list="repLst"
            @update="fetchOptions($event, selected)"
            size="sm"
            v-model="selected"
            type="search"></b-form-input>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import axios from "axios";
import _debounce from 'lodash/debounce';

export default {
  name: 'SearchInput',
  data() {
    return {
      selected: '',
      searchResult: []
    }
  },
  methods: {
    fetchOptions(search) {
      if (search.length > 2) {
        this.search(search, this)
      } else {
        localStorage.setItem("movieLst", JSON.stringify([{"Title": "No value"}]))
        this.$emit('changedEvent')
      }
    },
    search: _debounce((search, vm) => {
      vm.searchResult = []
      axios.get("http://localhost:8080/api/all?search=" + search)
          .then((res) => {
            vm.searchResult = res.data
            vm.searchResult[0].Search = search
            localStorage.setItem("movieLst", JSON.stringify(vm.searchResult))
            vm.$emit('changedEvent')
          })
          .catch((err) => {
            if(err.response){
              localStorage.setItem("movieLst", JSON.stringify([{"Title": "Not found", "Search": search }]))
              vm.$emit('changedEvent')
              return
            }
            console.error(err)
          })
    }, 500)
  }
}
</script>


<style scoped>
</style>
