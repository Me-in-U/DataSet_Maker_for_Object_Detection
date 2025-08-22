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
if (!isset($decoded['username']) || !isset($decoded['password'])) {
    echo json_encode(array("success" => false, "message" => "username or password is missing"));
    exit;
}

// POST 데이터 받기
$user_username = mysqli_real_escape_string($con, $decoded['username']);
$user_password = mysqli_real_escape_string($con, $decoded['password']);


// 입력된 비밀번호를 SHA2 해시로 변환
$hashed_password = hash('sha256', $user_password);

// 사용자 이름 중복 확인
$query = "SELECT * FROM users WHERE username = '$user_username'";
$result = mysqli_query($con, $query);
if (mysqli_num_rows($result) > 0) {
    echo json_encode(array("success" => false, "message" => "이미 등록된 아이디입니다."));
    mysqli_close($con);
    exit;
}

// 새 사용자 추가
$insertQuery = "INSERT INTO users (username, password) VALUES ('$user_username', '$hashed_password')";
if (mysqli_query($con, $insertQuery)) {
    echo json_encode(array("success" => true, "message" => "회원가입 완료. 로그인해주세요"));
} else {
    echo json_encode(array("success" => false, "message" => "알수없는 오류"));
}
mysqli_close($con);
?>