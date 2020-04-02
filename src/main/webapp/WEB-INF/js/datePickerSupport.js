 var today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
 var d = new Date(),
 month = '' + (d.getMonth() + 1),
 day = '' + d.getDate(),
 year = d.getFullYear();

if (month.length < 2) 
 month = '0' + month;
if (day.length < 2) 
 day = '0' + day;
var pickToday = [month,day,year].join('/');
console.log(pickToday);

 $('#fromDateInput').datepicker({
     uiLibrary: 'bootstrap4',
     icons: {
         rightIcon: '<i class="text-primary fa fa-calendar fa-lg"	aria-hidden="true"></i>'
     },
     keyboardNavigation: true,
     iconsLibrary: 'fontawesome',
     size: 'large',
     maxDate: today
 });
 
 
 $('#toDateInput').datepicker({
     uiLibrary: 'bootstrap4',
     icons: {
         rightIcon: '<i class="text-primary fa fa-calendar fa-lg"	aria-hidden="true"></i>'
     },
     iconsLibrary: 'fontawesome',size: 'large',
//     value : pickToday,
     minDate: function () {
         return $('#startDate').val();
     },
     maxDate: today
 	
 });