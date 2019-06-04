ComponentApp.controller('RepositoryCreationController', [ '$scope',
		'$location', '$log','$http', 'ComponentRepositoryService',
		function($scope, $location, $log, $http, ComponentRepositoryService) {
			
			$scope.repoStorage = ComponentRepositoryService;
			

			$scope.removeRepository = function(x) {
				$scope.repoStorage.deleteRepository(x);
			}

			$scope.editRepository = function(x) {
				$scope.repoStorage.editRepository(x);
				$location.path('/repos');
			}

			$scope.goToSingleRepositoryView = function() {
				$location.path('/repos');
			}

			$scope.downloadRepo = function() {
				var toSave = $scope.repoStorage.getRepository();
				var jsonString = angular.toJson(toSave, true);
				console.log("POST");
				$http({
					method : 'POST',
					url : '/api/repo/save'+ '/'+$scope.repoStorage.repoCollectionName,
					headers: {
	                      accept: 'application/zip'
	                  },
	                  responseType: 'arraybuffer',
	                  cache: false,
				}).then(function(response) {
					console.log("worked");
				}, function(response) {
					console.log(response);
					console.log("Error");
				});
			}

		} ]);
