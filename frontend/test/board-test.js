import React from 'react';
import {expect} from 'chai';
import {shallow, mount, render} from 'enzyme';
import Foo from '../foo';
import Board from '../board.js';

describe("A suite", function () {

	it("contains spec with an expectation", function() {
		expect(shallow(<Foo />).contains(<div className="foo" />)).to.equal(true);
	});

	it("contains spec with an expectation", function () {

		var wrapper = shallow(<Board data={{"locations":[],"persons":[],"width":"5","height":"5"}}/>);
		console.log("hej");
		console.log(wrapper.debug());

		// expect(wrapper
		// 	.contains(<div className="board" style="height:5px"/>)).to.equal(true);
	});


	// it("contains spec with an expectation", function() {
	// 	expect(shallow(<Foo />).is('.foo')).to.equal(true);
	// });
	//
	// it("contains spec with an expectation", function() {
	// 	expect(mount(<Foo />).find('.foo').length).to.equal(1);
	// });
});
