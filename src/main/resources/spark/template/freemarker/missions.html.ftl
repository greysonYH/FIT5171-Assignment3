<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="missions" type="java.util.List<flymetomars.model.Mission>" -->

<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Fly me to Mars: a mission registration system.</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Fly me to Mars - a mission registration system.">
</head>

<body>
<div id="title_pane">
    <h3>Mission Listing Page</h3>
</div>

<div>
    <p><a href="/mission/create">Create Mission</a> </p>
    <p><a href="/mission/create">Update Mission</a> </p>
<#if errorMsg?? && errorMsg?has_content>
    <li><h4 class="errorMsg">${errorMsg}</h4></li>
<#elseif missions?? && missions?has_content>
    <p>Mission List</p>
    <ul>
        <#list missions as mission>
            <li>Mission name: <a href="/mission/${mission.id}">${mission.name}</a>, with Captain ${mission.captain.firstName} ${mission.captain.lastName}, on ${mission.time?datetime}, at ${mission.location}.</li>
        </#list>

    </ul>
<#else>
    <p>No mission yet in the system. <a href="/mission/create">Create one</a> now!</p>
</#if>
    <a href="/">Back</a>
</div>

</body>
</html>