$(document).ready(
    function() {
        var excelImport = getCookie("UploadStatus");
        if (excelImport == "true") {
            $("#importExcelSuccess").css("display", "block");
            Cookies.remove('UploadStatus', {
                path: ''
            });
        }
        else if (excelImport == "false") {
            $("#importExcelFailed").css("display", "block");
            Cookies.remove('UploadStatus', {
                path: ''
            });
        }
        var deleteCourses = getCookie("DeleteStatus");
        console.log(deleteCourses);
        if(deleteCourses == "true") {
        	$("#deleteCoursesSuccess").css("display", "block");
        	Cookies.remove('DeleteStatus', {
                path: ''
            });
        } else if (deleteCourses == "false") {
        	$("#deleteCoursesFailed").css("display", "block");
        	Cookies.remove('DeleteStatus', {
                path: ''
            });
        }
        var username = getCookie("Username");
        if (username == "") {
            window.location.href = "login";
        }
        else {
            $("body").css('display', 'block');
            $(".username").text(username + " | Uitloggen");
        }
        $(".logout").click(function() {
            Cookies.remove('Username', {
                path: ''
            });
            window.location.href = "login";
        });

        $.ajax({
                type: 'POST',
                data: {
                    username: username,
                    type: "getLastExportNumber",
                },
                url: 'ExcelController',
                success: function(result) {
                    if (result == "logout") {
                        Cookies.remove('Username', {
                            path: ''
                        });
                        window.location.href = "login";
                    }
                    else {
                        var numbers = result.split(",");
                        if (numbers[0] == "0") {
                            $("#lastExport")
                                .text(
                                    "geen nieuwe inschrijvingen");
                        }
                        else {
                            $("#lastExport")
                                .text(
                                    numbers[0] +
                                    " nieuwe inschrijvingen");
                        }
                        if (numbers[1] == "0") {
                            $("#lastExportAdministration")
                                .text(
                                    "geen nieuwe inschrijvingen");
                        }
                        else {
                            $("#lastExportAdministration")
                                .text(
                                    numbers[1] +
                                    " nieuwe inschrijvingen");
                        }

                    }
                }
            });

        $(".custom-file-input").on(
            "change",
            function() {
                var fileName = $(this).val().split("\\").pop();
                $(this).siblings(".custom-file-label")
                    .addClass("selected").html(fileName);
            });
    });

$("#nav-registration-export").click(function() {
    $("#registration-export").css("display", "block");
    $("#financial-registration-export").css("display", "none");
    $("#course-management").css("display", "none");
    $("#waiting-list-export").css("display", "none");
});

$("#nav-financial-registration-export").click(function() {
    $("#registration-export").css("display", "none");
    $("#financial-registration-export").css("display", "block");
    $("#course-management").css("display", "none");
    $("#waiting-list-export").css("display", "none");
});

$("#nav-course-management").click(function() {
    $("#registration-export").css("display", "none");
    $("#financial-registration-export").css("display", "none");
    $("#course-management").css("display", "block");
    $("#waiting-list-export").css("display", "none");
});

$("#nav-waiting-list-export").click(function() {
	$("#registration-export").css("display", "none");
    $("#financial-registration-export").css("display", "none");
    $("#course-management").css("display", "none");
    $("#waiting-list-export").css("display", "block");
});

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}