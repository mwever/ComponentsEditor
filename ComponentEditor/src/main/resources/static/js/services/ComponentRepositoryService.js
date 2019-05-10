			ComponentApp.service('ComponentRepositoryService', ['$log', '$http', function($log, $http) {
			
				this.componentArray = [];
				this.componentToEdit = null;
				this.editMode = false;
				
				
				this.repositoryArray = [];
				this.repositoryToEdit = null;
				this.editModeRepo = false;
				
				
				this.addComponent = function(component) {
					this.componentArray.push(component);
					this.editMode = false;
				};
				
				this.getComponents = function() {
					return this.componentArray;
				};
				
				this.checkComponent = function(x){
					for(var i = 0; i < this.componentArray.length; i++){
						if(x.name == this.componentArray[i].name){
							return true
						}
					}

				};
				
				this.updateComponent = function(x){
					for(var i = 0; i < this.componentArray.length; i++){
						if(x.name == this.componentArray[i].name){
							this.componentArray[i].name = x.name;
							this.componentArray[i].requiredInterfaces = x.requiredInterfaces;
					        this.componentArray[i].providedInterfaces = x.providedInterfaces;
					        this.componentArray[i].parameters = x.parameters;
					        this.componentArray[i].dependencies = x.dependencies;
						}
					}
					this.editMode = false;
				}
				
				this.deleteComponent = function(x){
					
					$http({
						method: 'DELETE',
						url: 'http://localhost:8080/components'+'/'+this.componentArray[x].name,
					}).then(
							function(response){
								console.log("Delete worked");
							},
							function(response){
								console.log(response);
								console.log("Error");
							}		
			    	);
					this.componentArray.splice(x,1);
				};
				
				this.editComponent = function(x){
					this.componentToEdit = this.componentArray[x];
					this.editMode = true;
				};
				
				this.toLoad = function(){
					return this.editMode;
				};
				
				this.getToLoadComponent = function(){
					return this.componentToEdit;
				};
				
				//----------------------------------------------------------------------------------------
				
				this.addRepository = function(repository) {
					this.repositoryArray.push(repository);
					this.editModeRepo = false;
				};
				
				this.getRepository = function() {
					return this.repositoryArray;
				};
				
				this.checkRepository = function(x){
					for(var i = 0; i < this.repositoryArray.length; i++){
						if(x.name == this.repositoryArray[i].name){
							return true
						}
					}

				};
				
				this.updateRepository = function(x){
					for(var i = 0; i < this.repositoryArray.length; i++){
						if(x.name == this.repositoryArray[i].name){
							this.repositoryArray[i].name = x.name;
							this.repositoryArray[i].components = x.components;
					        
						}
					}
					this.editMode = false;
				}
				
				this.deleteRepository = function(x){
					
					$http({
						method: 'DELETE',
						url: 'http://localhost:8080/repos'+'/'+this.repositoryArray[x].name,
					}).then(
							function(response){
								console.log("Delete worked");
							},
							function(response){
								console.log(response);
								console.log("Error");
							}		
			    	);
					this.repositoryArray.splice(x,1);
				};
				
				this.editRepository = function(x){
					this.repositoryToEdit = this.repositoryArray[x];
					this.editModeRepo = true;
				};
				
				this.toLoadRepo = function(){
					return this.editModeRepo;
				};
				
				this.getToLoadRepo = function(){
					return this.repositoryToEdit;
				};
				
			}]);