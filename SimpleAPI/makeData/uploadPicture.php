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

// POST 데이터 검증
if (!isset($decoded['topicId']) || !isset($decoded['picture']) || !isset($decoded['x1']) || !isset($decoded['y1']) || !isset($decoded['x2']) || !isset($decoded['y2']) || !isset($decoded['className'])) {
    echo json_encode(array("success" => false, "message" => "Missing data"));
    exit;
}

// 변수 할당
$topicId = $decoded['topicId'];
$base64_image = $decoded['picture'];
$x1 = $decoded['x1'];
$y1 = $decoded['y1'];
$x2 = $decoded['x2'];
$y2 = $decoded['y2'];
$className = $decoded['className'];

// Base64 문자열을 BLOB 데이터로 변환합니다.
$image_data = base64_decode($base64_image);

// 이미지 데이터를 데이터베이스에 저장
$query = "INSERT INTO topic_images (topic_id, image_data, pos_x1, pos_y1, pos_x2, pos_y2, classname) VALUES (?, ?, ?, ?, ?, ?, ?)";
$stmt = mysqli_prepare($con, $query);

// $image_data 변수를 바인딩합니다. 'b' 대신 's'를 사용하여 문자열(BLOB 데이터)로 처리합니다.
mysqli_stmt_bind_param($stmt, "isiiiis", $topicId, $image_data, $x1, $y1, $x2, $y2, $className);

$result = mysqli_stmt_execute($stmt);

// 실행 결과 확인
if ($result) {
    echo json_encode(array("success" => true, "message" => "Image uploaded successfully"));
} else {
    echo json_encode(array("success" => false, "message" => "Error uploading image"));
}

mysqli_close($con);
?>