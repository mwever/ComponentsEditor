			ComponentApp.service('ComponentRepositoryService', ['$log', '$http', function($log, $http) {
			
				this.componentArray = [];
				
				this.addComponent = function(component) {
					this.componentArray.push(component);
				};
				
				this.getComponents = function() {
					return this.componentArray;
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