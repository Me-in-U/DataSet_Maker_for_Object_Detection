# 📱 DataSet Maker for Object Detection  

> 안드로이드 기반 **Object Detection 데이터셋 제작 도구**  
> 사진 촬영 → 객체 라벨링 → 데이터 업로드까지 한 번에 처리할 수 있는 올인원 툴  

---

## ✨ Features
- 🔐 **회원 관리**: 회원가입 / 로그인 / 로그아웃 지원  
- 📷 **카메라 Fragment**: TensorFlow Lite (MobileNet V1) 모델로 실시간 객체 탐지  
- 🏷 **데이터셋 생성**:  
  - 주제(Topic)별로 데이터셋 관리  
  - 객체(Class) 선택 및 바운딩 박스(BBox) 좌표 라벨링  
  - 사진/라벨을 MySQL 서버에 업로드  
- 👤 **마이페이지 Fragment**: 로그인된 사용자 정보 관리  

---

## 🛠 Tech Stack
### Frontend (Mobile App)
- Android (Java / Kotlin)
- TensorFlow Lite (Object Detection)

### Backend
- PHP (API 서버)
- Apache Web Server

### Database
- MySQL  
  ```sql
  CREATE TABLE image_data (
    id INT AUTO_INCREMENT PRIMARY KEY,
    topic_id INT,
    image_data BLOB,
    pos_x1 INT,
    pos_y1 INT,
    pos_x2 INT,
    pos_y2 INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    classname VARCHAR(50)
  );

## 📸 Screenshots

![스크린샷 2024-03-16 234933](https://github.com/BOJ-ios/data_set_maker_for_object_detection/assets/44316764/63178fe3-0c29-4e62-9ce6-1350bd4aabd8)
![스크린샷 2024-03-16 234948](https://github.com/BOJ-ios/data_set_maker_for_object_detection/assets/44316764/8fe68c1e-76a2-41bf-8e6e-ef8bca8e0e24)
![스크린샷 2024-03-16 234958](https://github.com/BOJ-ios/data_set_maker_for_object_detection/assets/44316764/79a12aab-0581-4c51-b0d4-b0a82a05db91)
![스크린샷 2024-03-16 235005](https://github.com/BOJ-ios/data_set_maker_for_object_detection/assets/44316764/2ad8a8c2-0728-42c7-9b67-f9c055cea805)
![스크린샷 2024-03-16 235009](https://github.com/BOJ-ios/data_set_maker_for_object_detection/assets/44316764/d7c74b86-bc7b-472d-b5fb-53b426b81f66)
