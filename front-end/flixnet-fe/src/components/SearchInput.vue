<template>
  <div>
    <v-select label="Title" :filterable="false" :options="searchResult" @search="fetchOptions">
      <template slot="no-options">
        type to search for movie titles
      </template>
    </v-select>
  </div>
</template>

<script>
import axios from "axios";
import vSelect from 'vue-select'
import _debounce from 'lodash/debounce';
export default {
  name: 'SearchInput',
  components: {
    vSelect
  },
  data () {
    return {
      searchResult : []
    }
  },
  methods: {
    fetchOptions(search, loading){
      if (search.length){
        loading(true)
        this.search(loading, search, this)
      }
    },
    search: _debounce((loading, search, vm) => {
      axios.get("http://localhost:8080/api/all?search=" + search)
          .then((res) => {
            console.log(res)
            vm.searchResult = res.data
            loading(false)
          })
          .catch((err) => {
            loading(false)
            console.error(err)
          })
    }, 500)
  }
}
</script>


<style scoped>
@import 'vue-select/dist/vue-select.css';
</style>
