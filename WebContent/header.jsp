      <!--=== Header v3 ===-->    
    <div style="background-color:white;" class="header-v3 header-sticky">
        <!-- Navbar -->
        <div class="navbar navbar-default mega-menu" role="navigation">
            <div class="container">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="fa fa-bars"></span>
                    </button>
                    <a class="navbar-brand" href="./home">
                        CodeCorpus
                    </a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse navbar-responsive-collapse">
                    <ul class="nav navbar-nav">
                        <!-- Home -->
                        <li>
                            <a href="./home" class="dropdown-toggle">
                                Home
                            </a>
                        </li>                       
                        <li>
                            <a href="./suggestions" class="dropdown-toggle">
                                Suggestions
                            </a>
                        </li> 
                        <li>
                            <a href="./profile" class="dropdown-toggle">
                                My Profile
                            </a>
                        </li> 
				        <li class="dropdown pull-right"> 
				       <a href="#" data-toggle="dropdown">Hi! <%=session.getAttribute("handle") %> <span class="glyphicon glyphicon-user pull-right"></span></a>
					          <ul class="dropdown-menu">
					            <li><a href="./accountUpdate">Account Settings <span class="glyphicon glyphicon-cog pull-right"></span></a></li>
					            <li><a href="./logout">Sign Out <span class="glyphicon glyphicon-log-out pull-right"></span></a></li>
					    	</ul>    
					    	</li>    
                    </ul>
                </div><!--/navbar-collapse-->               
            </div>    
        </div>            
        <!-- End Navbar -->
    </div>
