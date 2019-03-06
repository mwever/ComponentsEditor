class Component{
    constructor(name, reqInterface, providedInterface, param, dependency){
        this.name = name;
        this.requiredInterfaces = reqInterface;
        this.providedInterfaces = providedInterface;
        this.parameters = param;
        this.dependencies = dependency;
    }
}; 

class ReqInterface{
    constructor(id, name /*, prio*/){
        this.id = id;
        this.name = name;
        //this.prio = prio;
    }
};  

class ProvidedInterface{
    constructor(interfaces){
        this.interfaces = interfaces;
    }
};

class Parameter{
    constructor(name , paramtype, defaultValue /*, prio  */){
        this.name = name;
        this.defaultDomain = paramtype;
        this.defaultValue = defaultValue;
        //this.prio = prio;
    }
};

class Dependency{
    constructor(pre, post){
        this.pre = pre;
        this.post = post;
    }
}; 

class CategoricalParameterDomain{
    constructor(values){
        /*this.defaultVal = defaultVal;
        this.name = 'cat';*/
        this.values = values;
    }
}

class NumericParameterDomain{
    constructor(min,max,isInteger){
       /* this.name = 'number';*/
        this.min = min;
        this.max = max;
        this.isInteger = isInteger;
       /* this.defaultVal = defaultVal;*/
    }
}

class BooleanParameterDomain extends CategoricalParameterDomain{
    constructor(/*defaultVal*/){
        /*this.name = 'bool';
        this.defaultVal = defaultVal;*/
    	super(new Array("true","false"));
    }
}

/*class Kitten{
    constructor(name){
        this.name = name;
    }
}*/

ComponentApp.controller('ComponentCreationController',['$scope','$location','$http','ComponentRepositoryService', function($scope,$location,$http ,crs){
                            
   /* $scope.repro=[];

    $scope.components=[];*/
    
	//$scope.currentComponent = crs.getLatestComponent();
    $scope.componentName ="";
    $scope.reqInterfaces = [];
    $scope.providedInterfaces = [];
    $scope.parameters =[];
    $scope.dependencys=[];

    $scope.types =[{label: 'Cat', actualType: new CategoricalParameterDomain(new Array())},{label: 'Number', actualType: new NumericParameterDomain(null,null,false) },{label: 'Bool', actualType: new BooleanParameterDomain()}];

    $scope.addReqInterface = function(){
        $scope.errortext="";
        if(!($scope.reqInterfaces.indexOf($scope.addReqInterface)== -1)){
            $scope.errortext="The requiered Interface is allready added";
        }else{
            $scope.reqInterfaces.push(new ReqInterface(null,null /*,null*/));
        }
    }

    $scope.addProvidedInterface = function(){
        $scope.errortext = "";
        if(!($scope.providedInterfaces.indexOf($scope.addProvidedInterface)== -1)){
            $scope.errortext="The provided Interface is allready added";
        }
        else{
            $scope.providedInterfaces.push(new ProvidedInterface(null,null,null));
        }
    }

    $scope.addParameter = function(){
        $scope.errortext="";
        if(!($scope.parameters.indexOf($scope.addParameter)== -1)){
            $scope.errortext="The parameter is allready added";
        }else{
            $scope.parameters.push(new Parameter(null,null,null));
        }
    }

    $scope.addKitten = function(x){
        /* $scope.errortext="";
        if(!($scope.parameters.indexOf($scope.addParameter)== -1)){
            $scope.errortext="The parameter is allready added";
        }else{ */
    		
            $scope.parameters[x].defaultDomain.values.push("");
        //}
    }

    $scope.addDependency = function(){
        $scope.errortext="";
        if(!($scope.dependencys.indexOf($scope.dependencys)== -1)){
            $scope.errortext="The dependency is allready added";
        }else{
            $scope.dependencys.push(new Dependency(null,null));
        }
    }

    $scope.resetForm = function(){
        $scope.parameters = [];
        $scope.reqInterfaces =[];
        $scope.providedInterfaces=[];
        $scope.parameters=[];
        $scope.dependencys=[];
        $scope.componentName="";
    }


    $scope.addComponent=function(){
		//console.log("add component via the service");
    	var comp = new Component($scope.componentName,$scope.reqInterfaces,$scope.providedInterfaces,$scope.parameters,$scope.dependencys); 
		
		//console.log("added component to the service ", crs.getComponents());
		//console.log($scope.componentName);
        //$scope.components.push(new Component($scope.componentName,$scope.reqInterfaces,$scope.providedInterfaces,$scope.parameters,$scope.dependencys));
		
		
		console.log("Hello");
		
		var jsonString =  angular.toJson(comp, true);
		//var jsonString = JSON.stringify(comp);
		console.log(jsonString);
		
		if(crs.checkComponent(comp)){
			console.log("POST");
			$http({
				method: 'POST',
				url: 'http://localhost:8080/components',
				//Content-Type: 'application/json',
				data: jsonString  //comp
			}).then(
					function(response){
						console.log("worked");
					},
					function(response){
						console.log(response);
						console.log("Error");
					}		
	    	);
			
		}
		else{
			
			console.log("PUT");
			$http({
				method: 'PUT',
				url: 'http://localhost:8080/components',
				//Content-Type: 'application/json',
				data: jsonString //comp 
			}).then(
					function(response){
						console.log("worked");
					},
					function(response){
						console.log(response);
						console.log("Error");
					}
					
	    	);
		}
		
		crs.addComponent(comp);
		
		
		$scope.resetForm();
		//console.log("redirect to repo");
        $location.path('/');
    }
	
	/*$scope.getComponents = function() {
		return crs.getComponents();
	}*/

    $scope.removeReqInterface = function(x){
        $scope.reqInterfaces.splice(x,1);
    }

    $scope.removeProvidedInterface = function(x){
        $scope.errortext="";
        $scope.providedInterfaces.splice(x,1);
    }

    $scope.removeParameter = function(x){
        $scope.errortext="";
        $scope.parameters.splice(x,1);
    }

    $scope.removeDependency = function(x){
        $scope.errortext="";
        $scope.dependencys.splice(x,1);
    }

    $scope.removeKitten = function(x,y){
        $scope.parameters[$scope.parameters.indexOf(x)].defaultDomain.values.splice(y,1);
    }

    $scope.isNumber = function(x){
    	return x instanceof NumericParameterDomain;
    }
    
    $scope.isCat = function(x){
    	return (x instanceof CategoricalParameterDomain && !(x instanceof BooleanParameterDomain))
    }
    
    $scope.isBool = function(x){
    	return (x instanceof BooleanParameterDomain)
    }
    /*$scope.goToComponentView=function(){
        $location.path('');
    }*/
    
    /*$scope.removeComponent = function(x){
    	crs.deleteComponent(x);
    }*/
    
}
]);