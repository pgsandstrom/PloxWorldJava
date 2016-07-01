import React from 'react';
import {expect} from 'chai';
import {shallow, mount, render} from 'enzyme';
import Board from './board.js';

describe("A suite", function () {
	var lol = {"hej": "hej"};

	it("contains spec with an expectation", function () {

		var shallowBoard = shallow(<Board data={{"locations":[],"persons":[],"width":"5","height":"5"}}/>);
		console.log(shallowBoard.debug());

		expect(shallowBoard
			.contains(<div className="board" style={{...}}/>)).to.equal(true);
	});

	// it("contains spec with an expectation", function() {
	// 	expect(shallow(<Foo />).is('.foo')).to.equal(true);
	// });
	//
	// it("contains spec with an expectation", function() {
	// 	expect(mount(<Foo />).find('.foo').length).to.equal(1);
	// });
});
