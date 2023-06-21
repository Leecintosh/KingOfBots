<template>
  <NavBar />
  <RouterView></RouterView>
</template>

<script>
import NavBar from './components/NavBar.vue';
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
// import $ from 'jquery'

export default {
  components: {
    NavBar
  },
  setup() {
    // $.ajax({
    //   url: "http://127.0.0.1:3000/user/account/token/",
    //   type: "post",
    //   data: {
    //     username: "xul",
    //     password: "666",
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(err) {
    //     console.log(err);
    //   }
    // });

    // $.ajax({
    //   url: "http://127.0.0.1:3000/user/account/info/",
    //   type: "get",
    //   headers: {
    //     Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxNzJkZWY3ZDNmOTA0YmQ2YTNlNGIyNmM0ZTE1NDE2NyIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTY4NzM3MDIxNiwiZXhwIjoxNjg4NTc5ODE2fQ.7FAwMrkYzC-iqiyQVHBn5gtE-H9ViFdmLlDf5VmM_DY",
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(err) {
    //     console.log(err);
    //   }
    // });

    // $.ajax({
    //   url: "http://127.0.0.1:3000/user/account/register/",
    //   type: "post",
    //   data: {
    //     username: "xul2",
    //     password: "123",
    //     confirmedPassword: "1232",
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.log(resp);
    //   }
    // })
  }
}
</script>

<style>
body {
  background-image: url("@/assets/images/background.png");
  background-size: cover;
}
</style>
