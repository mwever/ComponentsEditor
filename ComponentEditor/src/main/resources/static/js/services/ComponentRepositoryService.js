			ComponentApp.service('ComponentRepositoryService', ['$log', '$http', function($log, $http) {
			
				this.repoCollectionName = "";
				
				this.componentArray = [];
				this.componentToEdit = null;
				this.editMode = false;
				
				
				this.repositoryArray = [];
				this.repositoryToEdit = null;
				this.editModeRepo = false;
				
				//-------------------------------------------------------------------------------------------------------
				
				this.componentName ="";
				this.reqInterfaces = [];
				this.providedInterfaces = [];
				this.parameters =[];
				this.dependencys=[];
				
				this.originalComp = null;
				
				this.getCompName = function(){
					return this.componentName;
				}
				
				this.getreqInterfaces = function(){
					return this.reqInterfaces;
				}
				
				this.getprovidedInterfaces = function(){
					return this.providedInterfaces;
				}
				
				this.getparameters = function(){
					return this.parameters;
				}
				
				this.getdependencys = function(){
					return this.dependencys;
				}
				
				
				
				//-------------------------------------------------------------------------------------------------------
				
				this.name = ""
				//this.components= []
					
				
				this.original = null;
				
				this.getname = function(){
					return this.name;
				}
				
				this.getcomponents = function(){
					return this.componentArray;
				}
				
				this.getoriginal = function(){
					return this.original;
				}
				
				//-------------------------------------------------------------------------------------------------------
				
				this.addComponent = function(component) {
					this.componentArray.push(component);
					this.editMode = false;
					this.componentToEdit= null;
				};
				
				/*this.getComponents = function() {
					return this.componentArray;
				};*/
				
				this.checkComponent = function(x){
					console.log(this.componentArray);
					if(this.componentArray.length == 0){
						return false;
					}
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
					this.componentToEdit = null;
				}
				
				this.deleteComponent = function(x){
					
					$http({
						method: 'DELETE',
						url: '/components'+'/'+this.componentArray[x].name,
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
					this.originalComp = this.componentToEdit;
					this.editMode = true;
				};
				
				this.changeComponentName = function(x,y){
					this.componentArray[this.componentArray.indexOf(x)]=y;
					this.editMode = false;
					this.componentToEdit = null;
					this.originalComp = null;
				};
				
				this.istoLoad = function(){
					return this.editMode;
				};
				
				this.getToLoadComponent = function(){
					return this.componentToEdit;
				};
				
				//----------------------------------------------------------------------------------------
				
				this.addRepository = function(repository) {
					this.repositoryArray.push(repository);
					this.repositoryToEdit = null;
					this.componentArray = [];
					this.componentToEdit = null;
					this.editMode = false;
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
					this.repositoryToEdit = null;
					this.componentArray = [];
					this.componentToEdit = null;
					this.editMode = false;
					this.editModeRepo = false;
					
				};
				
				this.deleteRepository = function(x){
					
					$http({
						method: 'DELETE',
						url: '/repos'+'/'+this.repositoryArray[x].name,
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
					this.componentArray = this.repositoryToEdit.components;
					this.name = this.repositoryToEdit.name;
					this.original = angular.copy(this.repositoryToEdit);
					this.editModeRepo = true;
				};
				
				this.toLoadRepo = function(){
					return this.editModeRepo;
				};
				
				this.getToLoadRepo = function(){
					return this.repositoryToEdit;
				};
				
			}]);