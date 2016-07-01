import React from 'react';
import {expect} from 'chai';
import {shallow, mount, render} from 'enzyme';
import Board from '../src/board.js';

describe("The board", function () {

	it("shall render correctly!!!", function () {
		var shallowBoard = shallow(<Board data={{"locations":[],"persons":[],"width":"5","height":"5"}}/>);
		// console.log(shallowBoard.debug());
		expect(shallowBoard.contains(<div className="board" style={{"width":"5px", "height":"5px"}}/>)).to.equal(true);
	});
});
