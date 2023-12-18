import { Button, Form, Input, message } from "antd";
import React from "react";
import { useNavigate } from "react-router-dom";
import { isLoggedIn } from "../../../services/auth";
import { IMetadataGroup } from "../../../types/metadatagroup";
import { addMetadataGroup } from "../../../services/metadataGroup";
import routes from "../../../routes";

const AddMetadataGroup: React.FC = () => {
  const navigate = useNavigate();

  const onFinish = async (values: any) => {
    const username = localStorage.getItem("username");

    if (isLoggedIn() && username) {
      const metadataGroup: IMetadataGroup = {
        id: 0,
        name: values.name,
        metadataDtoSet: values.metadataDtoSet,
      };

      await addMetadataGroup(metadataGroup)
        .then(() => {
          message.success("Thành công!");
          navigate(routes.ADMIN);
        })
        .catch(() => {
          message.error("Thất bại!");
        });
    } else {
      message.success("Time out");
      navigate(routes.LOGIN);
    }
  };

  const onFinishFailed = (errorInfo: any) => {
    console.log("Failed:", errorInfo);
  };

  return (
    <div>
      <Form
        name="newBlogForm"
        layout="vertical"
        labelCol={{ span: 8 }}
        initialValues={{ remember: true }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
      >
        <Form.Item
          label="Tiêu đề"
          name="name"
          rules={[{ required: true, message: "Hãy nhập tên Group!" }]}
        >
          <Input size="large" placeholder="MetadataGroup name!" />
        </Form.Item>

        <Button
          type="primary"
          className="bg-[#CD1818] hover:bg-[#6d6d6d] my-3"
          htmlType="submit"
        >
          Thêm mới
        </Button>
      </Form>
    </div>
  );
};

export default AddMetadataGroup;
