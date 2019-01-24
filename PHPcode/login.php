  <?php
  $conn = mysqli_connect("localhost", "root", "chamel186", "breaktime");
  if(!$conn)
  {
  die("Database connection failed: " .mysqli_error($conn));
  exit();
  }
  $ID=$_GET['ID'];
  $PW=$_GET['PW'];
  $sql = $conn->query("SELECT * FROM user WHERE nickname='$ID' AND password='$PW'");

  if(mysqli_num_rows($sql) > 0)
  {
  echo "Log in Ok";
  }
  else
  {
  echo "Log in Error";
  }
  mysqli_close($conn);

  ?>
