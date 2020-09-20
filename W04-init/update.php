<?php
  $link = mysqli_connect('localhost','root','rootroot','dbp');
  $query = "SELECT * FROM topic";
  $result = mysqli_query($link, $query);
  $list ='';
  
  while ($row = mysqli_fetch_array($result)) {
    $list = $list."<li><a href=\"index.php?id={$row['id']}\">{$row['title']}</a></li>";
  }

  $article = array(
    'title' => 'Welcome',
    'description' => 'Database is ...'
  );

  $updated_link = '';

  if( isset($_GET['id'])) {
    $query = "SELECT * FROM topic WHERE id={$_GET['id']}";
    $result = mysqli_query($link, $query);
    $row = mysqli_fetch_array($result);
    $article = array(
      'title' => $row['title'],
      'description' => $row['description']
    );
    $updated_link = '<a href="update.php?id'.$_GET['id'].'">update</a>';
  }

 ?>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>DATABASE</title>
  </head>
  <body>
    <h1><a href="index.php">DATABASE</a></h1>
    <ol><?= $list ?></ol>            
    <form action="process_update.php" method="POST">
      <input type="hidden" name="id" value="<?= $_GET['id'] ?>">
      <p><input type="text" name="title" placeholder="title" value="<?= $article['title'] ?>"></p>
      <p><textarea name="description" placeholder="description"><?= $article['description'] ?></textarea></p>
      <p><input type="submit"></p>
    </form>
  </body>
</html>
