import { CaretRightOutlined } from "@ant-design/icons";
import { Collapse, Typography, ConfigProvider } from "antd";
import { CSSProperties, useState } from "react";
import React from "react";
import { IReview } from "../types/reiview";

const { Panel } = Collapse;
const { Text } = Typography;

const getPanels: (
  panelStyle: CSSProperties,
  listReview: IReview[]
) => React.ReactNode = (panelStyle, listReview) => {
  const panelData: IReview[] = [];
  if (listReview.length === 0) {
    listReview = panelData;
  }
  return listReview.map((item) => (
    <Panel key={item.id} header={item.title} style={panelStyle}>
      <Text>{item.content}</Text>
    </Panel>
  ));
};

interface Props {
  listReview: IReview[];
}

const Reviews: React.FC<Props> = ({ listReview }) => {
  const panelStyle: CSSProperties = {
    marginBottom: 24,
    background: "#ffffff",
    borderRadius: "4px",
    border: "none",
  };

  const [activePanel, setActivePanel] = useState<string | string[]>(["1"]);
  const handlePanelChange = (key: string | string[]) => {
    setActivePanel(key);
  };

  return (
    <ConfigProvider direction="ltr">
      <Collapse
        className="w-full"
        bordered={false}
        activeKey={activePanel}
        onChange={handlePanelChange}
        expandIcon={({ isActive }) => (
          <CaretRightOutlined
            className={`panel-arrow ${isActive ? "panel-arrow-active" : ""}`}
            rotate={isActive ? 90 : 0}
          />
        )}
        style={{ background: "#ffffff" }}
      >
        {getPanels(panelStyle, listReview)}
      </Collapse>
    </ConfigProvider>
  );
};

export default Reviews;
