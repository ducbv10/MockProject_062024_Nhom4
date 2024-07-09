<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet">
<main class="login-form">
    <div class="cotainer">
        <div class="row justify-content-center">
            <div class="col-md-4">
                <div class="card">
                    <h3 class="card-header text-center">Welcome to LiveAuctioneers!</h3>
                    <div class="card-body">
                        <form method="POST" action="{{ route('login.custom') }}">
                            @csrf
                            <div class="form-group mb-3">
                                <small>EMAIL ADDRESS / USERNAME</small>
                                <input type="text" id="email" class="form-control" name="email" required
                                    autofocus>
                            </div>

                            <div class="form-group mb-3">
                                <small>PASSWORD</small>
                                <input type="password" id="password" class="form-control" name="password" required>
                                @if ($errors->has('emailPassword'))
                                    <span class="text-danger">{{ $errors->first('emailPassword') }}</span>
                                @endif
                            </div>

                            <div class="d-grid mx-auto">
                                <button type="submit" class="btn btn-danger btn-block">LOG IN</button>
                            </div>

                            <div class="form-group mb-3" style="text-align: center">
                                <div>
                                    <label>
                                        <a href="#">Forgot Password?</a>
                                    </label>
                                </div>
                            </div>

                            <div class="form-group mb-3" style="text-align: center">
                                <div>
                                    <label>
                                        <p>Don't have an account? <a href="{{ route('register-user') }}">Join</a></p>

                                    </label>
                                </div>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
