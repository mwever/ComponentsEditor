var ComponentApp = angular.module('ComponentCreationApp',["ngRoute","ngFileSaver","ngFileUpload"]);
            ComponentApp.config(function($routeProvider){
                $routeProvider
                	.when("/repos",{
					//controller: "ComponentCreationController",
					controller: "RepoitoryController",
                    templateUrl:"views/Repo.htm"
                	})
                    .when("/Comp",{
						controller: "ComponentCreationController",
                        templateUrl:"views/Component.htm"
                    })
                     .when("/",{
						controller: "RepositoryCreationController",
                        templateUrl:"views/Repoitories.htm"
                    })
            });