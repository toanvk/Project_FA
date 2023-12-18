import { PlusOutlined } from "@ant-design/icons";
import { Button, Modal, Space, Table, message } from "antd";
import { ColumnsType } from "antd/es/table";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import routes from "../../../routes";
import { logout } from "../../../services/auth";
import {
  deleteMetadataGroup,
  getAllMetaDataGroup,
} from "../../../services/metadataGroup";
import { IMetadataGroup } from "../../../types/metadatagroup";

const MetadataGroups: React.FC = () => {
  const navigate = useNavigate();
  const [dataSource, setDataSource] = useState([]);
  const [isDeleteOpen, setIsDeleteOpen] = useState(false);
  const [metadataGroupDelete, setMetadataGroupDelete] = useState<string>("");

  const buttonUpdate = (id: string) => {
    navigate(`admin/edit/${id}`);
  };

  const buttonDelete = (id: string) => {
    setIsDeleteOpen(true);
    setMetadataGroupDelete(id);
  };

  const doDelete = () => {
    if (metadataGroupDelete) {
      deleteMetadataGroup(metadataGroupDelete)
        .then((data: any) => {
          setIsDeleteOpen(false);
          setMetadataGroupDelete("");
          message.success(data.message);
        })
        .catch(() => {
          setIsDeleteOpen(false);
          setMetadataGroupDelete("");
          message.error("Có lỗi khi Metadata Group!!");
        });
    }
  };

  const cancelDelete = () => {
    setIsDeleteOpen(false);
  };

  useEffect(() => {
    getAllMetaDataGroup()
      .then((data: any) => {
        setDataSource(data);
      })
      .catch(() => {
        logout();
        navigate("/login");
        console.error("Failed to fetch metadata group information");
      });
  }, [navigate, metadataGroupDelete]);

  const columns: ColumnsType<IMetadataGroup> = [
    {
      title: "Tên Group",
      dataIndex: "name",
      key: "name",
    },

    {
      title: "Action",
      key: "action",
      render: (_, record) => {
        return (
          <Space size="middle">
            <Button onClick={() => buttonUpdate(record.id.toString())}>
              Sửa
            </Button>
            <Button danger onClick={() => buttonDelete(record.id.toString())}>
              Xóa
            </Button>
          </Space>
        );
      },
    },
  ];

  return (
    <div>
      <Button
        href={routes.ADMIN_METADATAGROUPS_ADDNEW}
        className="mb-4"
        danger
        icon={<PlusOutlined />}
      >
        Thêm Metadata Group
      </Button>

      <Table rowKey="id" columns={columns} dataSource={dataSource} />

      <Modal
        okButtonProps={{ style: { backgroundColor: "#CD1818" } }}
        title="Xóa metadata Group!"
        open={isDeleteOpen}
        onOk={doDelete}
        onCancel={cancelDelete}
        okText="Xóa"
      >
        <div className="flex flex-col items-center">
          Tất cả nội dung trong group này sẽ tự động chuyển sang group mặc định!
        </div>
      </Modal>
    </div>
  );
};

export default MetadataGroups;
