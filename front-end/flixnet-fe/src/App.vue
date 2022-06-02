<template>
  <div id="app" class="bg-dark">
    <WeissHeader/>
    <SearchInput @changedEvent="changeEvt()"/>
    <SearchResults :key="changed"/>
  </div>
</template>

<script>
import SearchInput from './components/SearchInput.vue'
import WeissHeader from "@/components/WeissHeader";
import SearchResults from "@/components/SearchResults";

export default {
  name: 'App',
  components: {
    SearchInput,
    WeissHeader,
    SearchResults
  },
  data () {
    return {
      changed: false
    }
  },
  methods: {
    changeEvt(){
      this.changed = !this.changed
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
