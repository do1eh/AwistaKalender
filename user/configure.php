<?php

 
   include("../templateoben.php");  
   
   $result = $conn->query("SELECT * FROM user WHERE id='".$_SESSION['userid']."'");
   $datensatz = $result->fetch_assoc();

   ?>

<center>


<h1><?=configuretitle?></h1>
<?=configureexplain?>
    <form action="checkconfigure.php" method="post">
    <?php
       echo '<select id="scoutgroup" name="scoutgroup">'; 
       $result2 = $conn->query("SELECT * FROM scoutgroup");
       $datensaetze = $result2->fetch_all(MYSQLI_ASSOC);
       foreach($datensaetze as $datensatz2) {
            echo '<option value = "'.$datensatz2["id"].'"';
            if ($datensatz["scoutgroup"]==$datensatz2["id"]) {echo' selected ';};
            echo'>' .$datensatz2["name"] .' - '.$datensatz2["association"];
       }
       echo '</select><br><br>';
    ?>
        <button type="submit" name='editscoutgroup'><?=buttoneditscoutgroup?></button> 
        <br><br>
        <input type="text" name="username" placeholder="<?=username?>" value='<?=$datensatz["username"] ?>' required>
        <input type="password" name="password" placeholder="<?=password?>" >
        <input type="password" name="password2" placeholder="<?=repeatpassword?>" > 
        <?php
       
       if($msg!='') {
         echo'<span style="color:red;">'.$msg.'<br></span>';
       }
       ?>
      
 <button type="submit"><?=buttoneditdata?></button>
 <button type="submit" name='abbrechen'><?=buttoncancel?></button>
    </form>



</center>

<?php
  include("../templateunten.php");
  ?>
