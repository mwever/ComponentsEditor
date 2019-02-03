ComponentApp.factory('ComponentServerService', ['$log','$q', '$http', function($log,$q ,$http) {
 	return{};
 		/*getAllComponents: function (){
 				return $http.get('http://localhost:8080/component').then(
 					function(response){
 						return response.data;
 					}
 				);
 			},
 			
 			createComponent: function(component){
 				return $http.post('http://localhost:8080/component', comp).then(
 					function(response){
 						return response.data;
 					},
 					function(errResponse){
 						console.error('error while creating a component');
 						return $q.reject(errResponse);
 					}
 				);
 			},
 			
 		 updateComponent: function(comp, name){
            return $http.put('http://localhost:8080/component'+name, comp).then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while updating component');
                        return $q.reject(errResponse);
                    }
            );
        },
     
        deleteComponent: function(name){
            return $http.delete('http://localhost:8080/component'+name)
            .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while deleting component');
                        return $q.reject(errResponse);
                    }
            	);
        	}
 	};*/
}]);