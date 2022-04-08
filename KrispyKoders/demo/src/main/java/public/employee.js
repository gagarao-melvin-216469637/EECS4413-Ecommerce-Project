angular.module('demo',  [])
.controller('Employee', function($scope, $http) {
    
    //TODO: get list of items, filter items, show list of items
    
    
    //get the list of employees
    $http.get('http://localhost:8080/employees').
        then(function(response) {
            $scope.employees = response.data._embedded.employeeList;
        });
        
    //filter deleted record
    $scope.deletedFilter = function (item) { 
	    return !item.deleted; 
	};
        
    $scope.add = function () {
		var hasNewRecord = false;
		//check if another new record is still pending
		angular.forEach($scope.employees, function(employee) {
			if(employee.id == null && !employee.deleted)
  				hasNewRecord = true;
		});
		if(!hasNewRecord)
			//add a new record
			$scope.employees.push({id:null});
    }; 
    
    $scope.delete = function(id){
		angular.forEach($scope.employees, function(employee) {
			if(employee.id == id){
				//mark the record as deleted
  				employee.deleted = true;
			}
		});
	};
	
	$scope.save = function(){
		angular.forEach($scope.employees, function(employee) {
			if(employee.deleted){
				if(employee.id != null){
					//delete record
				    $http.delete('http://localhost:8080/employees/' + employee.id).
				        then(function(response) {
							var index = $scope.employees.indexOf(employee);
		  					$scope.employees.splice(index, 1);  
				        });
				}
			} else if(employee.id == null){
				//create new record
			    $http.post('http://localhost:8080/employees', employee).
			        then(function(response) {
						employee.id = response.data.id;
			        });
			} else {
				//edit existing record
			    $http.put('http://localhost:8080/employees/' + employee.id, employee).
			        then(function(response) {});
			}
		});
	};
});