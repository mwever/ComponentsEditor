class Component{
    constructor(name, reqInterface, providedInterface, param, dependency){
        this.name = name;
        this.reqInterface = reqInterface;
        this.providedInterface = providedInterface;
        this.param = param;
        this.dependency = dependency;
    }
}; 

class reqInterface{
    constructor(id, name, prio){
        this.id = id;
        this.name = name;
        this.prio = prio;
    }
};  

class providedInterface{
    constructor(interfaces){
        this.interfaces = interfaces;
    }
};

class param{
    constructor(name, prio, paramtype){
        this.name = name;
        this.prio = prio;
        this.paramtype = paramtype;
    }
};

class dependency{
    constructor(pre, post){
        this.pre = pre;
        this.post = post;
    }
}; 

class cat{
    constructor(defaultVal){
        this.defaultVal = defaultVal;
        this.name = 'cat';
        this.cats = [];
    }
}

class number{
    constructor(min,max,defaultVal){
        this.name = 'number';
        this.min = min;
        this.max = max;
        this.defaultVal = defaultVal;
    }
}

class boolVal{
    constructor(defaultVal){
        this.name = 'bool';
        this.defaultVal = defaultVal;
    }
}

class kitten{
    constructor(name){
        this.name = name;
    }
}

ComponentApp.controller('ComponentCreationController',['$scope','$location', function($scope,$location){
                            
    $scope.repro=[];

    $scope.components=[];
    
    $scope.componentName =[];

    $scope.reqInterfaces = [];
    $scope.providedInterfaces = [];
    $scope.parameters =[];
    $scope.dependencys=[];

    $scope.types =[new cat(null),new number(null,null,null), new boolVal(null)];

    $scope.addReqInterface = function(){
        $scope.errortext="";
        if(!($scope.reqInterfaces.indexOf($scope.addReqInterface)== -1)){
            $scope.errortext="The requiered Interface is allready added";
        }else{
            $scope.reqInterfaces.push(new reqInterface(null,null,null));
        }
    }

    $scope.addProvidedInterface = function(){
        $scope.errortext = "";
        if(!($scope.providedInterfaces.indexOf($scope.addProvidedInterface)== -1)){
            $scope.errortext="The provided Interface is allready added";
        }
        else{
            $scope.providedInterfaces.push(new providedInterface(null,null,null));
        }
    }

    $scope.addParameter = function(){
        $scope.errortext="";
        if(!($scope.parameters.indexOf($scope.addParameter)== -1)){
            $scope.errortext="The parameter is allready added";
        }else{
            $scope.parameters.push(new param(null,null,null));
        }
    }

    $scope.addKitten = function(x){
        /* $scope.errortext="";
        if(!($scope.parameters.indexOf($scope.addParameter)== -1)){
            $scope.errortext="The parameter is allready added";
        }else{ */
            $scope.parameters[x].paramtype.cats.push(new kitten(null));
        //}
    }

    $scope.addDependency = function(){
        $scope.errortext="";
        if(!($scope.dependencys.indexOf($scope.dependencys)== -1)){
            $scope.errortext="The dependency is allready added";
        }else{
            $scope.dependencys.push(new dependency(null,null));
        }
    }

    $scope.reset = function(){
        $scope.parameters = [];
        $scope.reqInterfaces =[];
        $scope.providedInterfaces=[];
        $scope.parameters=[];
        $scope.dependencys=[];
        $scope.componentName=[];
    }


    $scope.addComponent=function(){
        $scope.components.push(new Component($scope.componentName[0],$scope.reqInterfaces,$scope.providedInterfaces,$scope.parameters,$scope.dependencys));
        $scope.reset();
        $location.path('/Repro');
    }


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
        $scope.parameters[$scope.parameters.indexOf(x)].paramtype.cats.splice(y,1);
    }

    $scope.removeComponent=function(x){
        $scope.components.splice(x,1);
    }

    $scope.goToComponentView=function(){
        $location.path('');
    }
}
]);