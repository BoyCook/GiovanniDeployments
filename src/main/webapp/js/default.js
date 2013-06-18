var http = new HTTPService();
$().xslt('functions', {
    beforeLoad: function() {
        $('#loading').dialog('open');
    },
    afterLoad: function() {
        $('#loading').dialog('close');
    }
});

function setup() {
    $('#butExpandAll, #butCollapseAll').button();
    $('#loading').dialog();

    $('#butExpandAll').click(function() {
        $('.resource_div').collapsible('open');
    });
    $('#butCollapseAll').click(function() {
        $('.resource_div').collapsible('collapse');
    });
}

function setupProjects() {
    setup();
    $('#content').xslt({
        xslUrl: 'xsl/projects.xsl',
        xmlUrl: 'service/projects',
        filterable: true,
        filterKey: ['filter1', 'filter2', 'filter3'],
        callBack: function() {
            $('.resource_div').collapsible(true);
        },
        filterCallBack: function() {
            $('.resource_div').collapsible(true);
        }
    });
}

function setupServers() {
    setup();
    var type = document.location.hash.substring(1);
    $('#content').xslt({
        xslUrl: 'xsl/apps.xsl',
        xmlUrl: 'service/servers/' + type,
        filterable: true,
        filterKey: ['filter1', 'filter2', 'filter3'],
        callBack: function() {
            $('.resource_div').collapsible(true);
        },
        filterCallBack: function() {
            $('.resource_div').collapsible(true);
        }
    });
}
