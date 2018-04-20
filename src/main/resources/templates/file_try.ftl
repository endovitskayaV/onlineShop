<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</head>

<body>
<div>
    <p class="statusMsg"></p>
    <form enctype="multipart/form-data" id="fupForm" method="post" action="/items/files">
        <div class="form-group">
            <label for="name">NAME</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Enter name"/>
        </div>
        <div class="form-group">
            <label for="name">FIELD</label>
            <input type="number" class="form-control" id="field" name="field" placeholder="Enter field"/>
        </div>
        <div class="form-group">
            <label for="file">File</label>
            <input type="file" class="form-control" id="file" name="file" required/>
        </div>
        <input type="submit" class="btn btn-danger submitBtn" value="SAVE"/>
    </form>
</div>
<!--<script>
    $(document).ready(function () {
        $("#fupForm").on('submit', function (e) {
            e.preventDefault();
            var formData = new FormData();

            // formData.append('try', JSON.stringify({
            //             name: "Tom",
            //             field: 6
            //         }));
            formData.append('name', 'Tom');

            formData.append('field', 6);
            // Attach file
            formData.append('file', $('input[type=file]')[0].files[0]);
            $.ajax({
                type: 'POST',
                url: document.location.origin + '/items/files',
                data: formData,
                cache: false,
                contentType: false,
                processData: false,
                success: function (msg) {
                    $('.statusMsg').html('');
                    if (msg == 'ok') {
                        $('#fupForm')[0].reset();
                        $('.statusMsg').html('<span style="font-size:18px;color:#34A853">Form data submitted successfully.</span>');
                    } else {
                        $('.statusMsg').html('<span style="font-size:18px;color:#EA4335">Some problem occurred, please try again.</span>');
                    }
                    $('#fupForm').css("opacity", "");
                    $(".submitBtn").removeAttr("disabled");
                },
                error: function (data) {
                    alert("no");
                }
            });
        });

        //file type validation
        $("#file").change(function () {
            var file = this.files[0];
            var imagefile = file.type;
            var match = ["image/jpeg", "image/png", "image/jpg"];
            if (!((imagefile == match[0]) || (imagefile == match[1]) || (imagefile == match[2]))) {
                alert('Please select a valid image file (JPEG/JPG/PNG).');
                $("#file").val('');
                return false;
            }
        });
    });
</script>-->
</body>
</html>