<?php
/*
 * Created on 31.12.2024
 * updated on 31.12.2024
 *
 
 */#

  
  include("../templateoben.php");
  if ($_SESSION['role']!='admin') {
    header('location: ../menu/main.php');
    exit(1);
 } 
?>

<center>


<h1>Administration</h1>
    <form action="checkadmin.php" method="post">
 


 <button type="submit" name="adminuser"><?=adminmenuuser?></button><br><br>
 <button type="submit" name="admingroup"><?=adminmenugroup?></button><br><br>
 <button type="submit" name="adminevent"><?=adminmenuevent?></button><br><br>
 <button type="submit" name="images"><?=adminmenuimages?></button><br><br>
 <button type="submit" name="back"><?=adminmenuback?></button>

 </form>



</center>

<?php
  include("../templateunten.php");
  ?>
