import { Button, Form, Input, message } from "antd";
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { IMetadataGroup } from "../../../types/metadatagroup";
import { isLoggedIn, logout } from "../../../services/auth";
import routes from "../../../routes";
import {
  editMetadataGroup,
  getMetadataGroupById,
} from "../../../services/metadataGroup";

const EditMetadataGroup: React.FC = () => {
  const navigate = useNavigate();
  const param: any = useParams();

  const [metadataGroup, setMeatadataGroup] = useState<IMetadataGroup>();

  const onFinish = async (values: any) => {
    const username = localStorage.getItem("username");
    if (isLoggedIn() && username && metadataGroup) {
      const newGroup: IMetadataGroup = metadataGroup;
      newGroup.name = values.name;
      newGroup.metadataDtoSet = values.metadataDtoSet;

      await editMetadataGroup(newGroup)
        .then(() => {
          message.success("Thành công!");
          navigate(routes.ADMIN_METADATAGROUPS);
        })
        .catch(() => {
          message.error("Thất bại!");
        });
    } else {
      message.success("Hết hạn, đăng nhập lại!");
      navigate(routes.LOGIN);
    }
  };

  const onFinishFailed = (errorInfo: any) => {
    console.log("Failed:", errorInfo);
  };

  useEffect(() => {
    if (param) {
      getMetadataGroupById(param.id)
        .then((data: IMetadataGroup) => {
          setMeatadataGroup(data);
          console.log(data);
        })
        .catch(() => {
          message.error("Có lỗi khi lấy dữ liệu!");
          logout();
          navigate("/admin/login");
        });
    }
  }, [navigate, param]);

  return (
    <div>
      {metadataGroup && (
        <Form
          name="newBlogForm"
          layout="vertical"
          labelCol={{ span: 8 }}
          initialValues={metadataGroup}
          onFinish={onFinish}
          onFinishFailed={onFinishFailed}
          autoComplete="off"
        >
          <Form.Item
            label="Tiêu đề"
            name="name"
            rules={[{ required: true, message: "Hãy nhập tiêu đề!" }]}
          >
            <Input size="large" placeholder="Tiêu đề nhãn hàng!" />
          </Form.Item>

          <Button
            type="primary"
            className="bg-[#CD1818] hover:bg-[#6d6d6d] my-3"
            htmlType="submit"
          >
            Cập nhật
          </Button>
        </Form>
      )}
    </div>
  );
};

export default EditMetadataGroup;
