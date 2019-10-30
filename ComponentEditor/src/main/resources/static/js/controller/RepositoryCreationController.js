ComponentApp.controller('RepositoryCreationController', [ '$scope',
		'$location', '$log','$http', 'FileSaver', 'Blob', 'Upload' ,'ComponentRepositoryService',
		function($scope, $location, $log, $http,FileSaver, Blob, Upload ,ComponentRepositoryService) {
			
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
				$scope.repoStorage.original = angular.copy(repoToEdit);
				
				
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
			
			$scope.downloadRepository = function(x){
				
				var repoTodownload = $scope.repoStorage.repositoryArray[x]
				var nameOfRepoToDownload = repoTodownload.name
				
				console.log("POST");
				$http({
					method : 'POST',
					url : '/api/repo/save/single'+'/'+nameOfRepoToDownload,
					responseType: 'arraybuffer'
	                
				}).then(function(response) {
					console.log("worked");
						var data = new Blob([response.data], { type: 'application/json' });
						FileSaver.saveAs(data, nameOfRepoToDownload+'.json');
				}, function(response) {
					console.log(response);
					console.log("Error");
				});
			}
			
			$scope.uploadRepo = function(files){
				$scope.files = files;
				if (files && files.length) {
					angular.forEach(files, function(file){
			        file.upload = Upload.upload({
			        	method : 'POST',
			        	url : '/api/repo/upload/zip',
			        	file : file
			        });
					console.log(file.name)
					file.upload.then(
							 function (response) {
								 console.log('worked')
								 $scope.getAllRepo();
				                if (response.status > 0)
				                    $scope.errorMsg = response.status + ': ' + response.data;
				            },
				            function(response){
				            	console.log(response)
				            	console.log(response.status)
				            });
			      });
				}
			}
			
			$scope.getAllRepo = function(){
				$http({
					method : 'GET',
					url : '/api/repo',
					responseType: 'arraybuffer'
	                
				}).then(function(response) {
					console.log("worked");
						//var data = new Blob([response.data], { type: 'application/json' });
						
						var data = response.data;
						var enc = new TextDecoder("utf-8");
						var json = enc.decode(data);
						data = JSON.parse(json);
						
						//var repositoryCollection = JSON.parse(data);
						console.log(data.length);
						
						console.log(data);
						var i;
						//console.log("Hallo1");
						//console.log(data.length);
						repos = [];
						
						for(i = 0; i < data.length; i++){
							var t = Object.assign(new Repository, data[i]);
							/*console.log("######");
							console.log(t);*/
							t.assignToObjects();
							repos.push(t);
						}
						//console.log(repos[0].components[0] instanceof Component);
						
						for (i = 0; i < data.length; i++) {
							//console.log("Hallo2");
							  if($scope.repoStorage.checkRepository(data[i])){
								  $scope.repoStorage.updateRepository(data[i]);
							  }else{
								  $scope.repoStorage.addRepository(data[i]);
								  //console.log(data[i])
								 
							  }
							}
						
						/*for (i = 0; i < data.length; i++) {
								console.log("Hallo3");
							 console.log($scope.repoStorage.getRepository());
							}*/
						
						
				}, function(response) {
					console.log(response);
					console.log("Error");
				});
			}
			
		} ]);
	