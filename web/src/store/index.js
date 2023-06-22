import { createStore } from 'vuex'
import ModuleUser from './user'
import createPersistedState from "vuex-persistedstate";

export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user: ModuleUser,
  },
  plugins: [createPersistedState({
    // localStorage: store data in localStorage, lose data when close browser
    // sessionStorage: store data in sessionStorage, lose data when close tab
    storage: window.localStorage,
    reducer(val) {
      return {
        user: val.user,
      }
    }
  })],
})
