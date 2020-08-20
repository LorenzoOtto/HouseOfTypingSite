var totalPrice = 0.00;
var secondCoursePrice;
var coursePrice;
var amountSecondCourses = 0;

function getAllCorrectInformation() {
	var courseCode = null;
	if($("#course_option-1").is(":checked")) {
		courseCode = $("#courseCode").val();
	}
	var parentsName = "";
	if($("#course-type-1").is(":checked")) {
		parentsName = $("#parentsName").val();
	}
	// Payment
	var bank = "";
	var nameAccountHolder = "";
	var iban = "";
	if($(".fullPayment").is(":checked")) {
		bank = $("#bank").val();
	} else {
		nameAccountHolder = $("#nameAccountHolder").val();
		iban = $("#iban").val();
	}
	
	var allMainInformation = "";
	allMainInformation = allMainInformation + courseCode;
	allMainInformation = allMainInformation + "," + $("#gender").val();
	allMainInformation = allMainInformation + "," + $("#birthday").val();
	allMainInformation = allMainInformation + "," + $("#firstName").val();
	if($("#insertion").val()=="") {
		allMainInformation = allMainInformation + "," + "";
	} else {
	allMainInformation = allMainInformation + "," + $("#insertion").val();
	}
	allMainInformation = allMainInformation + "," + $("#lastName").val();
	allMainInformation = allMainInformation + "," + parentsName;
	allMainInformation = allMainInformation + "," + $("#streetName").val();
	allMainInformation = allMainInformation + "," + $("#town").val();
	allMainInformation = allMainInformation + "," + $("#addressNr").val();
	allMainInformation = allMainInformation + "," + $("#zipCode").val();
	allMainInformation = allMainInformation + "," + $('#email').val();
	allMainInformation = allMainInformation + "," + $("#phoneNumber").val();
	allMainInformation = allMainInformation + "," + bank;
	allMainInformation = allMainInformation + "," + nameAccountHolder;
	allMainInformation = allMainInformation + "," + iban;
	allMainInformation = allMainInformation + "," + $('input[name=payment_option]:checked').val();
	allMainInformation = allMainInformation + "," + parseFloat(totalPrice).toFixed(2);
	allMainInformation = allMainInformation + "," + parseFloat(coursePrice).toFixed(2);
	return allMainInformation;
}

function getAllSecondInformation() {
	var allSecondInformation = "";
	$(".secondPersonContainer").each(function() {
		if($(this).css("display")!="none") {
			allSecondInformation = allSecondInformation + $(this).find(".secondGender").val();
			allSecondInformation = allSecondInformation + "," + $(this).find(".secondBirthday").val();
			allSecondInformation = allSecondInformation + "," + $(this).find(".secondFirstName").val();
			if($(this).find(".secondInsertion").val()=="") {
				allSecondInformation = allSecondInformation + "," + "";
			} else {
				allSecondInformation = allSecondInformation + "," + $(this).find(".secondInsertion").val();
			}
			allSecondInformation = allSecondInformation + "," + $(this).find(".secondLastName").val();
			allSecondInformation = allSecondInformation + "," + parseFloat(secondCoursePrice).toFixed(2);
			allSecondInformation = allSecondInformation + ":";
		}
	});
	return allSecondInformation;
}

function calculatePrice(price) {
	var coursePrice4 = Math.ceil(parseInt(price)/100*110/4);
	$("#coursePrice4").text(coursePrice4.toFixed(2));
	var coursePrice12 = Math.ceil(parseInt(price)/100*115/12);
	$("#coursePrice12").text(coursePrice12.toFixed(2));
}

function addSecondPerson() {
	amountSecondCourses++;
	totalPrice = parseInt(totalPrice) + parseInt(secondCoursePrice);
	$(".coursePrice").text(totalPrice);
	calculatePrice(totalPrice);
}

function removeSecondPerson() {
	if(amountSecondCourses > 0) {
		totalPrice = parseInt(totalPrice) - parseInt(secondCoursePrice);
		$(".coursePrice").text(totalPrice);
		calculatePrice(totalPrice);
	}
	amountSecondCourses--;
}

function resetPrice(price, secondPrice) {
	totalPrice = price;
	coursePrice = price;
	secondCoursePrice = secondPrice;
	$(".secondCoursePrice").text(secondPrice);
	$(".coursePrice").text(price);
	calculatePrice(price);
	mountSecondCourses = 0;
}

$('.split').on('input', function() {
	$(this).attr('type', 'tell');
	var myLength = $(this).val().trim().length;
	if (myLength == 1) {
		$(this).closest('.split').next('.split').focus().val('');
	}
});

$("#courseForm").submit(function() {
	$("#courseCodeLoader").css("display", "block");
	var code = "";
	$('.code').each(function() {
		code = code + $(this).val();
	});
	if (code.length == 9) {
		$.ajax({
			type : 'POST',
			data : {
				code : code,
				type : "getCourse"
			},
			url : 'CourseController',
			success : function(result) {
				$("#courseCodeLoader").css("display", "none");
				if(result=="false") {
					$("#wrongCode").css("display", "block");
					$("#codeSection").css("display", "none");
				} else {
					var course = result.split(":");
					if (course[0]=="true") {
						$("#codeSection").css("display", "none");
						$("#courseIsFullBefore").css("display", "block");
						$("#noPayment").css("display", "block");
						$("#payment").css("display", "none");
						$("#schoolName").text(course[1]);
						$("#courseCode").val(course[4]);
						resetPrice(course[2], course[3]);
						$("#registrationForm").css("display", "block");
						$('#bank').removeAttr('required');
						$('#termsPayment').removeAttr('required');
						$('#termsNoPayment').prop("required", true);
						 $("#nameAccountHolder").removeAttr('required');
						 $("#iban").removeAttr('required');
					} else {
						$("#noPayment").css("display", "none");
						$("#payment").css("display", "block");
						var course = result.split(":");
						$("#schoolName").text(course[1]);
						$("#courseCode").val(course[4]);
						resetPrice(course[2], course[3]);
						$("#codeSection").css("display", "none");
						$("#registrationForm").css("display", "block");
					}
				}	
			}
		});
	}
	return false;
});

$("#registrationForm").submit(function() {
	$("#formSubmit").prop("disabled", "true");
	$("#formSubmitWithoutPayment").prop("disabled", "true");
	$("#processRegistration").css("display", "block");
	$("#registrationForm").css("display", "none");
	$("#beginSection").css("display", "none");
	$("#courseIsFullBefore").css("display", "none");
	setTimeout(function() {
		$("#processRegistration").css("display", "none");
		$("#processTakesLongerThenExpected").css("display", "block");
	}, 4000);
	$.ajax({
		type : 'POST',
		data : {
			allInformation : getAllCorrectInformation(),
			allSecondInformation : getAllSecondInformation(),
		},
		url : 'FormController',
		success : function(result) {
			console.log(result);
			$("#processTakesLongerThenExpected").css("display", "none");
			$("#processRegistration").css("display", "none");
			if(result=="false") {
				$("#registrationFailed").css("display", "block");
			} else if (result == "full") {
				$("#courseIsFullAfter").css("display", "block");
				$("#nameOfStudent").text($("#firstName").val());
			} else if (result == "saved") {
				$("#paymentSuccess").css("display", "block");
			}else {
				window.location.href = result;
			}
		}
	});
	return false;
});

$("#secondPerson").on('click', '.closeSecondPerson', function() {
	$(this).closest('.secondPersonContainer').remove();
	removeSecondPerson();
});

$("#secondPersonButton").click(function() {
	$('<div class="container-fluid text-white secondPersonContainer" style="background-color: #01ace4;"> <br> <div class="row justify-content-center align-items-center"> <br> <h1>Nog een deelnemer inschrijven.</h1> <button type="button" style="color: white" class="closeSecondPerson close"> <span>&times;</span> </button> <br> </div> <div class="row justify-content-center align-items-center"><h5 class="text-warning">Wij gaan er vanuit dat de toegevoegde deelnemer de cursus thuis wenst te doen.</h5></div> <div class="row justify-content-center align-items-center"> <div class="col col-sm-9 col-md-9 col-lg-9"> <div class="form-group text-center"> <div class="form-row"> <label for="gender" class="control-label col">Geslacht</label> <p class="col-1"></p> <label for="birthday" class="control-label col">Geboortedatum</label><br> </div> <div class="form-row"> <div class="form-check-inline col"> <select name="gender" required class="form-control secondGender col"><option value="">Kies een geslacht</option> <option value="m">Man</option> <option value="v">Vrouw</option></select> <p class="col-1"></p> <input type="text" name="birthday" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" class="form-control col secondBirthday" placeholder="(DD-MM-JJJJ)" required> </div> </div> </div> <div class="form-group text-center"> <div class="form-row"> <label for="firstName" class="control-label col">Voornaam</label> <p class="col-1"></p> <label for="insertion" class="control-label col">Tussenvoegsel</label><br> </div> <div class="form-row"> <div class="form-check-inline col"> <input type="text" name="firstName" class="form-control col secondFirstName" placeholder="Voornaam" required> <p class="col-1"></p> <input type="text" name="insertion" class="form-control col secondInsertion" placeholder="Tussenvoegsel"> </div> </div> </div> <div class="form-group text-center"> <div class="form-row"> <label for="lastName" class="control-label col">Achternaam</label> </div> <div class="form-row"> <div class="form-check-inline col"> <p class="col"></p> <input type="text" name="lastName" class="form-control col-6 secondLastName" placeholder="Achternaam" required> <p class="col"></p> </div> </div> </div> </div> </div><br><br><hr></div>').appendTo( "#secondPerson");
	addSecondPerson();
	return false;
});

$("#wrongCodeButton").click(function() {
	$('#courseForm').trigger("reset");
	$("#wrongCode").css("display", "none");
	$("#codeSection").css("display", "block");
	return false;
});

$(".payment_option").change(function() {
	if($(".fullPayment").is(":checked")) {
		$("#invoicePayment").css("display", "none");
		$("#fullPayment").css("display" ,"block");
		$('#bank').prop('required',true);
		 $("#nameAccountHolder").removeAttr('required');
		 $("#iban").removeAttr('required');
		 $("#nameAccountHolder").val('');
		 $("#iban").val('');
	} else {
		$("#invoicePayment").css("display", "block");
		$("#fullPayment").css("display" ,"none");
		$('#bank option').each(function () {
		    if (this.defaultSelected) {
		        this.selected = true;
		    }
		});
		$("#bank").removeAttr('required');
		$("#nameAccountHolder").prop('required',true);
		$("#iban").prop('required',true);
	} 
});

$(".course_option").change(function() {
	if($("#course_option-1").is(":checked")) {
		$("#codeSection").css("display", "block");
		$("#registrationForm").css("display", "none");
		$("#welcomeStudent").css("display", "block");
		$("#secondPerson").empty();
		$("#wrongCode").css("display", "none");
		$('#courseForm').trigger("reset");
		$("#registrationForm").trigger("reset");
		$("#courseIsFullBefore").css("display", "none");
	} else {
		$("#codeSection").css("display", "none");
		$("#wrongCode").css("display", "none");
		$("#courseIsFullBefore").css("display", "none");
		$("#registrationForm").css("display", "block");
		$("#welcomeStudent").css("display", "none");
		if($("#course-type-1").is(":checked")) {
			resetPrice("135.00", "115.00");
		} else if ($("#course-type-2").is(":checked")) {
			resetPrice("150.00", "135.00");
		}
		$("#secondPerson").empty();
		$("#registrationForm").trigger("reset");
	}
});

$(".course-type").change(function() {
	if($("#course-type-1").is(":checked")) {
		$("#lastNameColAfter").toggleClass("col");
		$("#lastName").toggleClass("col-12");
		$("#lastNameLabel").toggleClass("col-12");
		$("#labelEmail").text("E-mailadres ouder/verzorger *");
		$("#labelParentsName").css("display", "block");
		$("#parentsName").css("display", "block");
		resetPrice("135.00", "115.00");
		$("#secondPerson").empty();
	} else {
		$("#lastNameColAfter").toggleClass("col-1");
		$("#lastName").toggleClass("col-12");
		$("#lastNameLabel").toggleClass("col-12");
		$("#parentsName").css("display", "none");
		$("#labelEmail").text("E-mailadres *");
		$("#labelParentsName").css("display", "none");
		$(".lastNameLabelCol").css("display", "none");
		resetPrice("150.00", "135.00");
		$("#secondPerson").empty();
	}
});