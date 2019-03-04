			ComponentApp.service('ComponentRepositoryService', ['$log', '$http', function($log, $http) {
			
				this.componentArray = [];
				
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
					var contained = Boolean(this.componentArray.indexOf(x) > -1);
					return contained;
				};
				
				this.deleteComponent = function(x){
					this.componentArray.splice(x,1);
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