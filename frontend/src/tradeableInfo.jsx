import React from 'react';

const TradeableInfo = props => (
  <div>
    <div>
      <span className="cell">Info</span>
      <span className="cell">Storage</span>
      <span className="cell">Workers</span>
      <span className="cell">Effectivity</span>
      <span className="cell">Buy</span>
      <span className="cell">Sell</span>
    </div>
    <div>
      <span className="cell">Money</span>
      <span className="cell">{props.tradeable.money}</span>
    </div>
    <div>
      <span className="cell">Commodity</span>
      <span className="cell">{props.tradeable.commodity.storage}</span>
      <span className="cell">{props.tradeable.commodity.workers}</span>
      <span className="cell">{props.tradeable.commodity.multiplier}</span>
      <span className="cell">{props.tradeable.commodity.buyPrice}</span>
      <span className="cell">{props.tradeable.commodity.sellPrice}</span>
    </div>
    <div>
      <span className="cell">Material</span>
      <span className="cell">{props.tradeable.material.storage}</span>
      <span className="cell">{props.tradeable.material.workers}</span>
      <span className="cell">{props.tradeable.material.multiplier}</span>
      <span className="cell">{props.tradeable.material.buyPrice}</span>
      <span className="cell">{props.tradeable.material.sellPrice}</span>
    </div>
    <div>
      <span className="cell">Construction</span>
      <span className="cell">{props.tradeable.construction.storage}</span>
      <span className="cell">{props.tradeable.construction.workers}</span>
      <span className="cell">{props.tradeable.construction.multiplier}</span>
      <span className="cell">{props.tradeable.construction.buyPrice}</span>
      <span className="cell">{props.tradeable.construction.sellPrice}</span>
    </div>
    <div>
      <span className="cell">Crystal</span>
      <span className="cell">{props.tradeable.crystal.storage}</span>
      <span className="cell">{props.tradeable.crystal.workers}</span>
      <span className="cell">{props.tradeable.crystal.multiplier}</span>
      <span className="cell">{props.tradeable.crystal.buyPrice}</span>
      <span className="cell">{props.tradeable.crystal.sellPrice}</span>
    </div>
    <div>
      <span className="cell">Science</span>
      <span className="cell">{props.tradeable.science.storage}</span>
      <span className="cell">{props.tradeable.science.workers}</span>
      <span className="cell">{props.tradeable.science.multiplier}</span>
      <span className="cell">{props.tradeable.science.buyPrice}</span>
      <span className="cell">{props.tradeable.science.sellPrice}</span>
    </div>
  </div>
);
TradeableInfo.propTypes = {
  tradeable: React.PropTypes.object.isRequired,
};

export default TradeableInfo;
