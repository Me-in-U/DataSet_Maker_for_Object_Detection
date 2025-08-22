<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');
// 데이터베이스 연결 설정
$host = "";
$dbUsername = "";
$dbPassword = "";
$dbName = "";

// MySQL 연결
$con = mysqli_connect($host, $dbUsername, $dbPassword, $dbName);

// 연결 확인
if (mysqli_connect_errno()) {
    die("Database connection failed: " . mysqli_connect_error());
}

// JSON 응답을 위한 헤더 설정
header('Content-Type: application/json');
$content = trim(file_get_contents("php://input"));
$decoded = json_decode($content, true);

// 요청 데이터 검증
if (!isset($decoded['id'])) {
    echo json_encode(array("success" => false, "message" => "ID is missing"));
    exit;
}

// POST 방식으로 id 받기
$imageID = mysqli_real_escape_string($con, $decoded['id']);

// id에 해당하는 이미지 데이터와 classname을 검색
$query = "SELECT image_data,pos_x1,pos_y1,pos_x2,pos_y2, classname FROM topic_images WHERE id = '$imageID'";
$result = mysqli_query($con, $query);

// 결과를 배열로 변환
if ($row = mysqli_fetch_assoc($result)) {
    $imageData = base64_encode($row['image_data']); // BLOB 데이터를 base64로 인코딩
    $x1 = $row['pos_x1'];
    $y1 = $row['pos_y1'];
    $x2 = $row['pos_x2'];
    $y2 = $row['pos_y2'];
    $classname = $row['classname'];

    // JSON으로 변환하여 출력
    echo json_encode(array("success" => true, "photo" => $imageData, "x1"=>$x1, "y1"=>$y1,"x2"=>$x2, "y2"=>$y2, "className" => $classname));
} else {
    // 이미지가 없는 경우
    echo json_encode(array("success" => false, "message" => "Image not found"));
}

// 연결 종료
mysqli_close($con);
?>