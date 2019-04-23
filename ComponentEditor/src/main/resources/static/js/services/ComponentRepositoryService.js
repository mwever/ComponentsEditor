			ComponentApp.service('ComponentRepositoryService', ['$log', '$http', function($log, $http) {
			
				this.componentArray = [];
				this.componentToEdit = null;
				this.editMode = false;
				
				class Component{
				    constructor(name, reqInterface, providedInterface, param, dependency){
				        this.name = name;
				        this.reqInterface = reqInterface;
				        this.providedInterface = providedInterface;
				        this.param = param;
				        this.dependency = dependency;
				    }
				}; 
				
				this.addComponent = function(component) {
					this.componentArray.push(component);
				};
				
				/*
				this.getLatestComponent = function(){
					return this.componentArray[this.componentArray.length-1];
				};
				
				this.addnewComponent = function(){
					this.componentArray.push(new Component(null,null,null,null,null));
				};*/
				
				this.getComponents = function() {
					return this.componentArray;
				};
				
				this.checkComponent = function(x){
					/*for(var i = 0; i < this.componentArray.length; i++){
						if(x.name == this.componentArray[i].name){
							return true
						}
					}*/
//					var contained = Boolean(this.componentArray.indexOf(x) > -1);
					return this.componentArray.includes[x];
				};
				
				this.updateComponent = function(x){
					
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
				/* this.sendComponentToServer = function(c) {
					$http.post('http://localhost:8080/api/component', c).then(function(r) {
					//success
					}, function(r) {
					// failed
					);
				}
			 */
			}]);