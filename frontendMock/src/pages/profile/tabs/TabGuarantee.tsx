import { Card } from "antd";
import { useEffect } from "react";
import { useSelector } from "react-redux";

const TabGuarantee: React.FC = () => {
  const username = useSelector((state: any) => state.user.username);

  useEffect(() => {
    if (username) {
    }
  }, [username]);

  return (
    <>
      <div className="container mx-auto">
        <Card title="Thông tin sản phẩm bảo hành">haha</Card>
      </div>
    </>
  );
};

export default TabGuarantee;
