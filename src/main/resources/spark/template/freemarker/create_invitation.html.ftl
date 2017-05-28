<#-- @ftlvariable name="description" type="java.lang.String" -->
<#-- @ftlvariable name="location" type="java.lang.String" -->
<#-- @ftlvariable name="time" type="java.lang.String" -->
<#-- @ftlvariable name="missionName" type="java.lang.String" -->
<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->

<#-- @ftlvariable name="budget" type="java.lang.String" -->
<#-- @ftlvariable name="duration" type="java.lang.String" -->


<#-- @ftlvariable name="person" type="java.util.Set<flymetomars.model.Person>" -->
<#-- @ftlvariable name="id" type="java.lang.String" -->
<#-- @ftlvariable name="recipientEmail" type="java.lang.String" -->
<#-- @ftlvariable name="captainEmail" type="java.lang.String" -->


<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Fly me to Mars: a mission registration system.</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Fly me to Mars: create mission.">
</head>

<body>

<div id="title_pane">
    <h3>Invitation Creation</h3>
</div>

<p>${errorMsg!""}</p>

<div>
    <p>* Fields are required.</p>
</div>
<form name="create_invitation" action="/mission/${id}/createInv" method="POST">

    <div id="admin_left_pane" class="fieldset_without_border">
        <div><p>Invitation Details</p></div>
        <ol>
            <li>
                <label for="missionId" class="bold">Mission ID: </label>
                <input id="missionId" name="missionId" type="text" value="${id!""}" readonly>
            </li>
            <li>
                <label for="missionName" class="bold">Mission Name: </label>
                <input id="missionName" name="missionName" type="text" value="${missionName!""}" readonly>
            </li>
            <li>
                <label for="captainEmail" class="bold">Captain Email: </label>
                <input id="captainEmail" name="captainEmail" type="text" value="${captainEmail!""}" readonly>
            </li>
            <li>
                <label for="recipient" class="bold">Recipient Email: </label>
                <input id="recipient" name="recipient" type="text" value="${recipientEmail!""}">
            </li>
            <li>
                <label for="timeUpdated" class="bold">Time Updated: </label>
                <input id="timeUpdated" name="timeUpdated" type="text" value="${.now?date!""}" readonly>
            </li>

        </ol>
    </div>

<#if errorMsg?? && errorMsg?has_content>
    <div id="error">
        <p>Error: ${errorMsg}</p>
    </div>
</#if>
    <div id="buttonwrapper">
        <button type="submit">Create Invitation</button>
        <a href="/">Cancel</a>
    </div>
</form>

</body>