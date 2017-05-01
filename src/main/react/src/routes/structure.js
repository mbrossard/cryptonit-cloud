import React from 'react';
import uuid from 'uuid';
import _ from 'underscore';

const CONFIG = [
    {
        slug: 'timestamping',
        title: 'Timestamping',
        icon: 'fa fa-clock-o fa-lg fa-fw',
        url: '/timestamping'
    }
];

const assignKeys = (input, level = 0) => _.map(input, (def) => {
    const newObj = { key: uuid.v4(), subMenuLevel: level };
    if(def.children) {
        newObj.children = assignKeys(def.children, level + 1);
    }
    return Object.assign({}, def, newObj);
});

export function urlMatcher(node, url) {
    if(node.matcher && !!url.match(node.matcher)) {
        return true;
    }

    return node.url === url;
}

export function findActiveNodes(nodes, url) {
    const activeNodes = [];

    const nodeIterator = (nodes) => {
        let found = false;

        nodes.forEach((node) => {
            if (node.children && nodeIterator(node.children)) {
                activeNodes.push(node);
                found = true;
            } else if (node.url && urlMatcher(node, url)) {
                activeNodes.push(node);
                found = true;
            }
        });

        return found;
    };

    nodeIterator(nodes);

    return activeNodes;
};

export function findSectionBySlug(nodes, slugName) {
    const getSections = function*(nodesTree) {
        for(let node of nodesTree) {
            yield node;

            if(node.children) {
                for(let section of getSections(node.children)) {
                    yield section;
                }
            }
        }
    };

    const sections = Array.from(getSections(nodes));

    return _.findWhere(sections, { slug: slugName });
}

export default assignKeys(CONFIG);
