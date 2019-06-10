ComponentApp.controller('RepositoryCreationController', [ '$scope',
		'$location', '$log','$http', 'FileSaver', 'Blob' ,'ComponentRepositoryService',
		function($scope, $location, $log, $http,FileSaver, Blob,ComponentRepositoryService) {
			
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
				
				console.log("POST");
				$http({
					method : 'POST',
					url : '/api/repo/save'+ '/'+$scope.repoStorage.repoCollectionName,
					responseType: 'arraybuffer'
	                
				}).then(function(response) {
					console.log("worked");
						var data = new Blob([response.data], { type: 'application/zip' });
						FileSaver.saveAs(data, $scope.repoStorage.repoCollectionName+'.zip');
				}, function(response) {
					console.log(response);
					console.log("Error");
				});
			}
			
			$scope.dirctEditing= function(x,y){
				
				var repo
				var repoToEdit
				for(repo in $scope.repoStorage.repositoryArray){
					if($scope.repoStorage.repositoryArray[repo].name == x){
						repoToEdit = $scope.repoStorage.repositoryArray[repo]
					}
				}
				console.log(repoToEdit)
				
				$scope.repoStorage.componentArray =  repoToEdit.components
				$scope.repoStorage.repositoryToEdit = repoToEdit
				$scope.repoStorage.editModeRepo = true
				
				$scope.repoStorage.name = x
				$scope.repoStorage.original = repoToEdit
				
				
				var comp
				var compToEdit
				for(comp in repoToEdit.components){
					if(repoToEdit.components[comp].name == y){
						compToEdit = repoToEdit.components[comp]
					}
				}
				
				$scope.repoStorage.componentToEdit = compToEdit
				$scope.repoStorage.editMode = true;
				 
				$scope.repoStorage.componentName = y;
				$scope.repoStorage.reqInterfaces = compToEdit.requiredInterfaces;
				$scope.repoStorage.providedInterfaces = compToEdit.providedInterfaces;
				$scope.repoStorage.parameters = compToEdit.parameters;
				$scope.repoStorage.dependencys= compToEdit.dependencies;
					
				$scope.repoStorage.originalComp = compToEdit;
				
				$location.path('/Comp');
				
			}
			
			//elem must have name property
			/*$scope.findElemByName = function(x,y,z){
				var index
				var elemToFind
				for(index in y){
					if(x == index.z){
						elemToFind = index
					}
				}
				return elemToFind
			}*/

		} ]);
