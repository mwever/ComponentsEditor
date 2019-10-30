class Component{
    constructor(name, reqInterface, providedInterface, param, dependency){
    console.log("create component " + name);
        this.name = name;
        this.requiredInterfaces = reqInterface;
        this.providedInterfaces = providedInterface;
        this.parameters = param;
        this.dependencies = dependency;
	    this.assignToObject = function() {
	    	console.log("Assign parameter class to parameter objects of component");
	        for(var i = 0; i<this.parameters.length; i++) {
	        	this.parameters[i] = Object.assign(new Parameter, this.parameters[i]);
	        	this.parameters[i].assignToObject();
	        }
	    };
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
    constructor(name , paramtype /*, prio  */){
        this.name = name;
        //this.paramTypeName = "";
        console.log("create parameter " + name + " with paramtype ", paramtype);
        if(paramtype.type=="cat") {
        	console.log("Create parameter domain for categorical parameters");
        	this.defaultDomain = new CategoricalParameterDomain(paramtype.values, paramtype.defaultValue);
        }
        
        //this.types = [new SelectionType('Cat',new CategoricalParameterDomain(new Array(),"")),new SelectionType('Number',new NumericParameterDomain(null,null,false,null)),new SelectionType('Bool',new BooleanParameterDomain())];
        //this.prio = prio;
	    this.assignToObject = function() {
	    	console.log("Assign parameter domain");
	    	
	    };
    }
};

class Dependency{
    constructor(pre, post){
        this.pre = pre;
        this.post = post;
    }
}; 

class CategoricalParameterDomain{
    constructor(values,defaultValue){
    	this.type = "cat";
        /*this.defaultVal = defaultVal;
        this.name = 'cat';*/
        this.values = values;
        this.defaultValue = defaultValue;
    }
}

class NumericParameterDomain{
    constructor(min,max,isInteger,defaultValue){
    	this.type = 'number';
        //this.type = "number";
    	this.min = min;
        this.max = max;
        this.isInteger = isInteger;
        this.defaultValue = defaultValue;
    }
}

class BooleanParameterDomain extends CategoricalParameterDomain{
    constructor(/*defaultVal*/){
        
        /*this.defaultVal = defaultVal;*/
    	super(new Array("true","false"),"");
    	this.type = 'bool';
    	//this.type = "bool";
    }
}

/*class SelectionType{
	constructor(TypeName, Type){
		this.label = TypeName;
		this.actualType = Type;
	}
}*/

class Kitten{
  constructor(name){
	  this.name = name;
  }
}


ComponentApp.controller('ComponentCreationController',['$scope','$location','$http','ComponentRepositoryService', function($scope,$location,$http,crs){
                            
   /* $scope.repro=[];

    $scope.components=[];*/
    
	//$scope.currentComponent = crs.getLatestComponent();
    /*$scope.componentName ="";
    $scope.reqInterfaces = [];
    $scope.providedInterfaces = [];
    $scope.parameters =[];
    $scope.dependencys=[];
    $scope.toLoad = null;*/
    
    //{label: 'Cat', actualType: new CategoricalParameterDomain(new Array())},{label: 'Number', actualType: new NumericParameterDomain(null,null,false) },{label: 'Bool', actualType: new BooleanParameterDomain()}
   

/*    $scope.componentToString = function(x){
    	var str = "";
    	if(x instanceof Component){
        	for(i in x){
        		for(j in i){
        			str+= j;
        		}
        	}
    	}
    	return str; 
    }*/
	$scope.compStorage = crs;
	
	/*$scope.isCat = false;
	$scope.isNumber = false;
	$scope.isBool= false;*/
	
	$scope.selection = "";
	$scope.selectionTypes = ["Cat","Number","Bool"]
    
	$scope.onChange = function(x,y){
		
		$scope.selection = y.selection;
		//console.log("onChange");
		if($scope.selection == "Cat"){
			/*$scope.isCat = true;
			$scope.isNumber = false;
			$scope.isBool= false;*/
			
			//console.log("onChangeCat");
			if(!(x.defaultDomain instanceof  CategoricalParameterDomain && !(x.defaultDomain instanceof  BooleanParameterDomain))){
				x.defaultDomain = new CategoricalParameterDomain(new Array(),"");
				//console.log("onChangeCatNew");
			}
		}
		if($scope.selection ==  "Number"){
			/*$scope.isNumbe = true;
			$scope.isCat = false;
			$scope.isBool= false;*/
			
			//console.log("onChangeNumber");
			if(!(x.defaultDomain instanceof  NumericParameterDomain)){
				x.defaultDomain = new NumericParameterDomain(null,null,false,null);
				//console.log("onChangeNumberNew");
			}
		}
		if($scope.selection ==  "Bool"){
			/*$scope.isBool= true;
			$scope.isCat = true;
			$scope.isNumber = false;
			*/
			//console.log("onChangeBool");
			if(!(x.defaultDomain instanceof  BooleanParameterDomain)){
				x.defaultDomain = new BooleanParameterDomain();
				//console.log("onChangeBoolNew");
			}
		}
	}
	
    $scope.addReqInterface = function(){
        $scope.errortext="";
        if(!(crs.reqInterfaces.indexOf($scope.addReqInterface)== -1)){
            $scope.errortext="The requiered Interface is allready added";
        }else{
            crs.reqInterfaces.push(new ReqInterface(null,null /*,null*/));
        }
    }

    $scope.addProvidedInterface = function(){
        $scope.errortext = "";
        if(!(crs.providedInterfaces.indexOf($scope.addProvidedInterface)== -1)){
            $scope.errortext="The provided Interface is allready added";
        }
        else{
            crs.providedInterfaces.push(new ProvidedInterface(null,null,null));
        }
    }

    $scope.addParameter = function(){
        $scope.errortext="";
        if(!(crs.parameters.indexOf($scope.addParameter)== -1)){
            $scope.errortext="The parameter is allready added";
        }else{
        	
            crs.parameters.push(new Parameter(null,null));
        }
    }

    $scope.addKitten = function(x){
        /* $scope.errortext="";
        if(!($scope.parameters.indexOf($scope.addParameter)== -1)){
            $scope.errortext="The parameter is allready added";
        }else{ */
    		
            crs.parameters[x].defaultDomain.values.push(new Kitten(""));
        //}
    }

    $scope.addDependency = function(){
        $scope.errortext="";
        if(!(crs.dependencys.indexOf($scope.dependencys)== -1)){
            $scope.errortext="The dependency is allready added";
        }else{
            crs.dependencys.push(new Dependency(null,null));
        }
    }

    $scope.resetForm = function(){
    	crs.parameters = [];
    	crs.reqInterfaces =[];
    	crs.providedInterfaces=[];
    	crs.parameters=[];
    	crs.dependencys=[];
    	crs.componentName="";
        
    	crs.componentToEdit = null;
        
    }
    

    $scope.addComponent=function(){
		
    	var comp = new Component(crs.componentName,crs.reqInterfaces,crs.providedInterfaces,crs.parameters,crs.dependencys); 
    	
		var jsonString =  angular.toJson(comp, true);
		
		if(crs.checkComponent(comp)){
			console.log("POST");
			$http({
				method: 'POST',
				url: '/components',
				data: jsonString
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
				url: '/components',
				data: jsonString 
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
		if(!crs.checkComponent(comp)){
			if(crs.editMode){
				crs.changeComponentName(crs.originalComp,comp);
			}else{
				crs.addComponent(comp);
			}
		}
		else{
			if(crs.originalComp.name != comp.name){
				crs.changeComponentName(crs.originalComp,comp);
			}
			crs.updateComponent(comp);
		}
		//console.log(crs.original)
		$scope.resetForm();
        $location.path('/repos');
    }
	
	/*$scope.getComponents = function() {
		return crs.getComponents();
	}*/

    $scope.removeReqInterface = function(x){
    	crs.reqInterfaces.splice(x,1);
    }

    $scope.removeProvidedInterface = function(x){
        $scope.errortext="";
        crs.providedInterfaces.splice(x,1);
    }

    $scope.removeParameter = function(x){
        $scope.errortext="";
        crs.parameters.splice(x,1);
    }

    $scope.removeDependency = function(x){
        $scope.errortext="";
        crs.dependencys.splice(x,1);
    }

    $scope.removeKitten = function(x,y){
    	crs.parameters[crs.parameters.indexOf(x)].defaultDomain.values.splice(y,1);
    }

    $scope.isNumber = function(x){
    	/*if(x instanceof NumericParameterDomain){
    		y.paramTypeName = "Number"
    	}*/
    	//console.log(x.defaultDomain)
    	return x.defaultDomain instanceof NumericParameterDomain;
    }
    
    $scope.isCat = function(x){
    	/*if(x instanceof CategoricalParameterDomain && !(x instanceof BooleanParameterDomain))
    	{	y.paramTypeName = "Cat";
    		}*/
    	console.log(x)
    	
    	console.log(x.defaultDomain instanceof CategoricalParameterDomain)
    	console.log(x.defaultDomain instanceof BooleanParameterDomain)
    	console.log(typeof x.defaultDomain)
    	return (x.defaultDomain instanceof CategoricalParameterDomain && !(x.defaultDomain instanceof BooleanParameterDomain))
    }
    
    $scope.isBool = function(x){
    	/*if(x instanceof BooleanParameterDomain){
    		y.paramTypeName ="Bool"
    	}*/
    	//console.log(x.defaultDomain)
    	//console.log(x.defaultDomain instanceof BooleanParameterDomain);
    	return (x.defaultDomain instanceof BooleanParameterDomain)
    }
    
    $scope.loadCheck = function(){
    	if(crs.editMode){
    		crs.componentToEdit = crs.getToLoadComponent();
    		console.log(crs.componentToEdit);
    		crs.componentName = crs.componentToEdit.name;
    		crs.reqInterfaces = crs.componentToEdit.requiredInterfaces;
    		crs.providedInterfaces = crs.componentToEdit.providedInterfaces;
    		crs.parameters = crs.componentToEdit.parameters;
    		crs.dependencys = crs.componentToEdit.dependencies;
    	}
    }
    
    $scope.inEditMode = function(){
    	//return crs.componentToEdit != null
    	return crs.editMode
    }
    
    $scope.inNormalMode = function(){
    	//return crs.componentToEdit == null
    	return !crs.editMode
    }
    /*$scope.goToComponentView=function(){
        $location.path('');
    }*/
    
    /*$scope.removeComponent = function(x){
    	crs.deleteComponent(x);
    }*/
    $scope.cancel = function(){
    	
    	if(crs.editMode){
    		crs.editMode= false;
    		
    		crs.updateComponent(crs.originalComp);
    		//$scope.componentsStorage.repositoryArray.push($scope.componentsStorage.originalRepo);
    		//console.log("in Edit Mode and to Load benutzt "+crs.originalComp)
    	}
    	
    	crs.editMode = false;
    	$scope.resetForm();
    	$location.path('/repos');
    }
}
]);