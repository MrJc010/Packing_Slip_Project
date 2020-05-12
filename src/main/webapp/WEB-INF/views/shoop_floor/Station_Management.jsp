<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Station Config"></c:param>
</c:import>


 <section class="station_management mt-5">
        <ul class="nav nav-tabs bg-light" id="myTab" role="tablist">
          <li class="nav-item">
            <a
              class="nav-link active"
              id="finished-station-tab"
              data-toggle="tab"
              href="#finished_station"
              role="tab"
              aria-controls="home"
              aria-selected="true"
            >
              <button type="button" class="btn btn-success">
                <strong>Finished Stations</strong>
                <span class="badge badge-light">4</span>
              </button>
            </a>
          </li>
          <li class="nav-item">
            <a
              class="nav-link"
              id="unfinished-station-tab"
              data-toggle="tab"
              href="#unfinished_station"
              role="tab"
              aria-controls="profile"
              aria-selected="false"
            >
              <button type="button" class="btn btn-warning">
                <strong class="text-danger">UnFinished Stations</strong>
                <span class="badge badge-light">4</span>
              </button></a
            >
          </li>
        </ul>
        <div class="tab-content" id="myTabContent">
          <!--
            -- ────────────────────────────────────────────────────────────────────────  ──────────
            --   :::::: F I N I S H E D   S T A T I O N : :  :   :    :     :        :          :
            -- ──────────────────────────────────────────────────────────────────────────────────
            -->

          <div
            class="tab-pane fade show active pl-5 pr-5 pt-2"
            id="finished_station"
            role="tabpanel"
            aria-labelledby="finished_station-tab"
          >
            <table class="table">
              <thead>
                <tr class="bg-success">
                  <th scope="col">#</th>
                  <th scope="col">Part Number</th>
                  <th scope="col">From Location</th>
                  <th scope="col">To Location</th>
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody>
                <tr class="table-success">
                  <th scope="row">1</th>
                  <td><strong>1234</strong></td>
                  <td><strong>ScanBox</strong></td>
                  <td><strong>ClosingBox</strong></td>
                  <!-- Setup Action -->
                  <!-- need list finished station with partnumber -->
                  <td>
                    <ul class="list-inline">
                      <li class="list-inline-item mr-5">
                        <a href="#edit"
                          ><i class="fa fa-pencil fa-lg" aria-hidden="true">
                            <strong class="ml-1">Edit</strong></i
                          ></a
                        >
                      </li>
                      <li class="list-inline-item">
                        <a href="#delete"
                          ><i
                            class="fa fa-pencil   fa-lg text-danger"
                            aria-hidden="true"
                          >
                            <strong class="ml-1 text-danger">Delete</strong></i
                          ></a
                        >
                      </li>
                    </ul>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <!--
            -- ──────────────────────────────────────────────────────────────────────────────  ──────────
            --   :::::: U N F I N I S H E D   S T A T I O N S : :  :   :    :     :        :          :
            -- ────────────────────────────────────────────────────────────────────────────────────────
            -->

          <div
            class="tab-pane fade show pl-5 pr-5 pt-2"
            id="unfinished_station"
            role="tabpanel"
            aria-labelledby="unfinished_station-tab"
          >
            <!-- Setup Action -->
            <!-- need list finished station with partnumber -->
            <table class="table">
              <thead>
                <tr class="bg-warning">
                  <th scope="col">#</th>
                  <th scope="col">Part Number</th>
                  <th scope="col">From Location</th>  
                  <th scope="col">Notes</th>                
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody>
                <tr class="table-warning">
                  <th scope="row">1</th>
                  <td>1234</td>
                  <td>NA</td>    
                  <td>Please Add Locations</td> 
                  <!-- Setup Action -->
                  <!-- need list finished station with partnumber -->
                  <td>
                    <ul class="list-inline">
                      <li class="list-inline-item mr-5">
                        <a href="#edit"
                          ><i class="fa fa-pencil  fa-lg" aria-hidden="true">
                            <strong class="ml-1">Edit</strong></i
                          ></a
                        >
                      </li>
                      <li class="list-inline-item">
                        <a href="#delete"
                          ><i
                            class="fa fa-pencil text-danger  fa-lg"
                            aria-hidden="true"
                          >
                            <strong class="ml-1 text-danger">Delete</strong></i
                          ></a
                        >
                      </li>
                    </ul>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </section>
<c:import url="/WEB-INF/common/footer.jsp"></c:import>