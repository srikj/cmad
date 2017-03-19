angular.module('Rendezvous').config(function($routeProvider){
  $routeProvider
    .when('/', {
      redirectTo: '/posts'
    })

    .when('/posts', {
      templateUrl: "assets/templates/posts/index.html",
      controller: "PostsIndexController"
    })

    .when('/posts/new', {
      templateUrl: "assets/templates/posts/new.html",
      controller: "PostsCreateController"
    })

    .when('/posts/:id', {
      templateUrl: "assets/templates/posts/show.html",
      controller: "PostsShowController"
    })

    .when('/posts/search/:key', {
      templateUrl: "assets/templates/posts/search.html",
      controller: "PostsSearchController"
    })

    .when('/posts/topic/:topic', {
      templateUrl: "assets/templates/posts/index.html",
      controller: "PostsTopicController"
    })

    .when('/posts/tag/:tag', {
      templateUrl: "assets/templates/posts/index.html",
      controller: "PostsTagController"
    })

    .when('/user/edit', {
      templateUrl: "assets/templates/user/edit.html",
      controller: "UserEditController"
    })

    .when('/user/login', {
      templateUrl: "assets/templates/user/login.html",
      controller: "UserLoginController"
    })

    .when('/user/register', {
      templateUrl: "assets/templates/user/register.html",
      controller: "UserRegisterController"
    })
});