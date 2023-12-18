import { UploadOutlined } from "@ant-design/icons";
import { Button, Image, Upload, UploadFile } from "antd";
import { UploadChangeParam } from "antd/es/upload";
import React, { useState } from "react";
import firebase from "firebase/compat/app";
import "firebase/compat/storage";

// Khởi tạo Firebase
const firebaseConfig = {
  apiKey: "AIzaSyCsPPlZT-GaGkBT23yEoTBbmZ6D4yGE-fI",
  authDomain: "famockproject-968a3.firebaseapp.com",
  databaseURL:
    "https://famockproject-968a3-default-rtdb.asia-southeast1.firebasedatabase.app",
  projectId: "famockproject-968a3",
  storageBucket: "famockproject-968a3.appspot.com",
  messagingSenderId: "429691655535",
  appId: "1:429691655535:web:1f8b0609b97d303a75c49a",
};

firebase.initializeApp(firebaseConfig);
const storage = (firebase as any).storage();

const UploadImage: React.FC = () => {
  const [selectedImages, setSelectedImages] = useState<File[]>([]);
  const [imageURLs, setImageURLs] = useState<string[]>([]);

  const handleFileInputChange = (info: UploadChangeParam<UploadFile<any>>) => {
    if (info.fileList && info.fileList.length > 0) {
      const files: any = info.fileList.map((file) => file.originFileObj);
      setSelectedImages(files);
    }
  };

  const handleUpload = () => {
    if (selectedImages.length > 0) {
      const uploadPromises = selectedImages.map((image) => {
        return storage.ref(`/images/${image.name}`).put(image);
      });

      Promise.all(uploadPromises)
        .then((snapshots) => {
          const downloadURLPromises = snapshots.map((snapshot) =>
            snapshot.ref.getDownloadURL()
          );
          return Promise.all(downloadURLPromises);
        })
        .then((urls) => {
          setImageURLs(urls);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  };

  return (
    <div>
      <Upload
        multiple
        beforeUpload={() => false}
        onChange={handleFileInputChange}
      >
        <Button icon={<UploadOutlined />}>Chọn ảnh</Button>
      </Upload>
      <Button type="primary" onClick={handleUpload}>
        Tải lên
      </Button>
      {imageURLs.map((url, index) => (
        <Image key={index} src={url} alt={`Uploaded ${index}`} />
      ))}
    </div>
  );
};

export default UploadImage;
