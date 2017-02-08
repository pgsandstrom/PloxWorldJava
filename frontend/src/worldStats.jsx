import React from 'react';

const WorldStats = (props) => {
  const data = [];
  for (const item in props.worldData.data) {
    data.push({ type: item, amount: props.worldData.data[item] });
  }

  return (
    <div className="worldStats">
      <h1>World Stats</h1>

      <div>
        <div>
          <span className="cellWide">Commodity:</span>
          <span className="cell">{props.worldData.productionData.commodity.storage}</span>
          <span className="cell">{props.worldData.productionData.commodity.production}</span>
        </div>
        <div>
          <span className="cellWide">Material</span>
          <span className="cell">{props.worldData.productionData.material.storage}</span>
          <span className="cell">{props.worldData.productionData.material.production}</span>
        </div>
        <div>
          <span className="cellWide">Construction</span>
          <span className="cell">{props.worldData.productionData.construction.storage}</span>
          <span className="cell">{props.worldData.productionData.construction.production}</span>
        </div>
        <div>
          <span className="cellWide">Crystal</span>
          <span className="cell">{props.worldData.productionData.crystal.storage}</span>
          <span className="cell">{props.worldData.productionData.crystal.production}</span>
        </div>
        <div>
          <span className="cellWide">Science</span>
          <span className="cell">{props.worldData.productionData.science.storage}</span>
          <span className="cell">{props.worldData.productionData.science.production}</span>
        </div>
      </div>

      <div>
        {data.map(dataItem =>
          <div key={dataItem.type}>
            <span className="cellWide">{dataItem.type}</span>
            <span className="cell">{dataItem.amount}</span>
          </div>,
          )}
      </div>
    </div>
  );
};
WorldStats.propTypes = {
  worldData: React.PropTypes.object.isRequired, //eslint-disable-line react/no-unused-prop-types
};

export default WorldStats;
