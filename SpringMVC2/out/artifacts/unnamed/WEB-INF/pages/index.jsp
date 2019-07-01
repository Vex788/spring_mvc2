<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Prog.kiev.ua</title>
      <style>
          body {
              background-color: black;
              text-align: center;
              color: white;
              font-family: roboto light;
              background: url(http://getwallpapers.com/wallpaper/full/b/d/0/606321.jpg)  white;
          }
          .container {
              margin: 25%;
              margin-left: 30%;
              margin-right: 30%;
              border-radius: 10px;
              padding: 10px;
              background: black;
              border: 0px solid black;
              text-align: center;
              opacity: 0.80;
          }
          .upload-btn-wrapper {
              position: relative;
              overflow: hidden;
              display: inline-block;
          }
          .btn {
              border: 1px solid gray;
              color: white;
              background-color: black;
              padding: 8px 20px;
              border-radius: 8px;
              font-size: 20px;
              -webkit-transition: all 0.3s ease;;
              -moz-transition: all 0.3s ease;;
              -o-transition: all 0.3s ease;;
              transition: all 0.3s ease;
          }
          .upload-btn-wrapper input[type=file] {
              font-size: 100px;
              position: absolute;
              left: 0;
              top: 0;
              opacity: 0;
          }
          input[type=text] {
              border: 1px solid gray;
              color: black;
              font-size: 17px;
              background-color: white;
              padding: 8px 20px;
              border-radius: 8px;
              -webkit-transition: all 0.3s ease;;
              -moz-transition: all 0.3s ease;;
              -o-transition: all 0.3s ease;;
              transition: all 0.3s ease;
          }
          .upload-btn-wrapper input[type=submit] {
              font-size: 100px;
              position: absolute;
              left: 0;
              top: 0;
              opacity: 0;
          }
      </style>
  </head>
  <body>
     <div class="container">
        <form action="/view" method="POST">
            <input type="text" placeholder="Photo ID" name="photo_id" />
            <input type="submit" class="btn" value="Submit" />
        </form>

        <form action="/add_photo" enctype="multipart/form-data" method="POST">
            <div class="upload-btn-wrapper">
                <button class="btn">Select a photo</button>
                <input type="file" name="photo" multiple="multiple" />
            </div>
            <div class="upload-btn-wrapper">
                <button class="btn">Upload</button>
                <input type="submit" />
            </div>
        </form><br>
         <input class="btn" type="submit" value="Show list" onclick="window.location='/show_list';"/>
     </div>
  </body>
</html>
