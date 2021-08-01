<template>
  <div class="contact-us full-screen">
    <nav class="navbar navbar-ct-default" role="navigation-demo">
      <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <router-link :to="{ path: '/home' }" class="navbar-brand"
            >Return to home</router-link
          >
        </div>
      </div>
    </nav>
    <div class="wrapper wrapper-full-page section content containercenter">
      <div class="chlidcenter">
        <div class="container">
          <div class="row">
            <div
              class="col-md-8 col-md-offset-2 text-center"
              style="width: -webkit-fill-available"
            >
              <h2 class="title text-danger" style="font-size: 80px">404 Not Found</h2>
              <h2 class="title" style="font-size: 50px">
                Oops! It seems that this page does not exist.
              </h2>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>
<style scoped>
.containercenter {
  display: flex;
  justify-content: center;
}
.chlidcenter {
  display: flex;
  vertical-align: middle;
}
</style>
